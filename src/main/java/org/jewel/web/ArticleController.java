package org.jewel.web;

import org.jewel.db.ArticleRepository;
import org.jewel.db.MetalTypeRepository;
import org.jewel.db.MineralRepository;
import org.jewel.model.Article;
import org.jewel.model.CollectionType;
import org.jewel.model.MetalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MetalTypeRepository metalTypeRepository;

    @Autowired
    private MineralRepository mineralRepository;

    private List<String> getCollectionTypesList() {
        List<String> collectionTypes = new ArrayList<>();
        for (CollectionType collectionType: CollectionType.values()) {
            collectionTypes.add(collectionType.name());
        }
        return collectionTypes;
    }

    @GetMapping(path = "/articles")
    public String articlesList(ModelMap modelMap) {
        List<Article> articles = articleRepository.findAllArticlesSorted();
        modelMap.addAttribute("allArticlesList",articles);
        return "articleList";
    }

    @GetMapping(path = "/article/add")
    public String addArticle(ModelMap modelMap) {
        Article article = new Article();
        modelMap.addAttribute("article", article);
        modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
        modelMap.addAttribute("metalTypeAttr",new MetalType());
        modelMap.addAttribute("collectionTypesList", CollectionType.values());
        modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
        return "addArticle";
    }

    @PostMapping(path = "/article/add")
    public String addArticlePost(ModelMap modelMap,
                             @ModelAttribute("metalTypeAttr")
                                     MetalType metalType,
                             @Validated
                             @ModelAttribute("article")
                             Article article,
                             BindingResult validationResult) {

        if (article.getMetalType() == null) {
            validationResult.addError(new FieldError("article", "metalType",
                    "Поле обязательно для заполнения"));
            modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
            return "addArticle";
        }
        article.setArticleName(article.getArticleName().trim());
        article.setDummyArticleName(article.getArticleName()+article.getMetalType().getMetalTypeName()+article.getMetalType().getHallmark());
        //    article.setMetalType(metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark(metalType.getMetalTypeName(),metalType.getHallmark()));
        if (validationResult.hasErrors()) {
            modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
            return "addArticle";
        }
        if (articleRepository.findArticleByArticleNameAndMetalType(article.getArticleName(),article.getMetalType())!=null) {
            validationResult.addError(new FieldError("article", "articleName",
                    "Артикул " + article.getArticleName() + " уже есть в базе."));
            modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
            return "addArticle";
        }

        articleRepository.save(article);
        return "redirect:/articles";
    }

    @GetMapping(path = "/article/{articleId}")
    public String editArticleGet(@PathVariable(name = "articleId") long articleId,
                                 ModelMap modelMap) {
        Article article = articleRepository.findArticleByArticleId(articleId);
        modelMap.addAttribute("article",article);
        modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
        modelMap.addAttribute("collectionTypesList", CollectionType.values());
        modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
        return "editArticle";
    }

    @PostMapping(path = "/article/{articleId}")
    public String editArticlePost(@PathVariable(name = "articleId") long articleId,
                                  ModelMap modelMap,
                                  @Validated
                                      @ModelAttribute("article")
                                              Article article,
                                  BindingResult validationResult,
                                  @Validated
                                  @ModelAttribute("metalTypeAttr")
                                          MetalType metalType,
                                  BindingResult metalTypeValidResult
                                 ) {
        if (article.getMetalType() == null) {
            validationResult.addError(new FieldError("article", "metalType",
                    "Поле обязательно для заполнения"));
            modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
            return "addArticle";
        }
//        metalType=metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark(metalType.getMetalTypeName(),metalType.getHallmark());
        article.setArticleName(article.getArticleName().trim());
        article.setDummyArticleName(article.getArticleName()+article.getMetalType().getMetalTypeName()+article.getMetalType().getHallmark());
//        article.setMetalType(metalType);
        if (validationResult.hasErrors()) {
            modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
            return "addArticle";
        }
        if (articleRepository.findArticleByArticleNameAndMetalType(article.getArticleName(),article.getMetalType())!=null &&
                (!article.getDummyArticleName().equals(articleRepository.findArticleByArticleId(articleId).getDummyArticleName()))){
            validationResult.addError(new FieldError("article", "articleName",
                    "Артикул " + article.getArticleName() + " уже есть в базе."));
            modelMap.addAttribute("metalTypeList",metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals() );
            return "addArticle";
        }

        articleRepository.save(article);
        return "redirect:/articles";
    }

    @GetMapping(path = "/article/delete/{articleId}")
    public String deleteArticle(@PathVariable(name = "articleId") long articleId) {
        Article article = articleRepository.findArticleByArticleId(articleId);
        articleRepository.delete(article);
        return "redirect:/articles";
    }

}
