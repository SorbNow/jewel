package org.jewel.controller;

import org.jewel.model.Article;
import org.jewel.model.CollectionType;
import org.jewel.model.MetalType;
import org.jewel.repos.ArticleRepository;
import org.jewel.repos.MetalTypeRepository;
import org.jewel.repos.MineralRepository;
import org.jewel.service.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MetalTypeRepository metalTypeRepository;

    @Autowired
    private MineralRepository mineralRepository;

    @Autowired
    private ArticleServiceImpl articleServiceImpl;


    @GetMapping
    public String articlesList(ModelMap modelMap) {
        List<Article> articles = articleServiceImpl.getArticleList();
        modelMap.addAttribute("allArticlesList", articles);
        return "articleList";
    }

    @GetMapping(path = "/add")
    public String addArticle(ModelMap modelMap) {
        Article article = new Article();
        modelMap.addAttribute("article", article);
        modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
        modelMap.addAttribute("metalTypeAttr", new MetalType());
        modelMap.addAttribute("collectionTypesList", CollectionType.values());
        modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
        return "addArticle";
    }

    @PostMapping(path = "/add")
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
            modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
            return "addArticle";
        }
        if (validationResult.hasErrors()) {
            modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
            return "addArticle";
        }
        if (articleRepository.findArticleByArticleNameAndMetalType(article.getArticleName(), article.getMetalType()) != null) {
            validationResult.addError(new FieldError("article", "articleName",
                    "Артикул " + article.getArticleName() + " уже есть в базе."));
            modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
            return "addArticle";
        }

        //    article.setMetalType(metalTypeRepository.findMetalTypeByMetalTypeNameAndHallmark(metalType.getMetalTypeName(),metalType.getHallmark()));
        article = articleServiceImpl.prepareArticleBeforeSaving(article);
        articleServiceImpl.saveArticle(article);
        return "redirect:/article";
    }

    @GetMapping(path = "/{articleId}")
    public String editArticleGet(@PathVariable(name = "articleId") long articleId,
                                 ModelMap modelMap) {
        Article article = articleRepository.findArticleByArticleId(articleId);
        modelMap.addAttribute("article", article);
        modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
        modelMap.addAttribute("collectionTypesList", CollectionType.values());
        modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
        return "editArticle";
    }

    @PostMapping(path = "/{articleId}")
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
            modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
            return "addArticle";
        }
        article = articleServiceImpl.prepareArticleBeforeSaving(article);
        if (validationResult.hasErrors()) {
            modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
            return "addArticle";
        }

        //TODO: сделать нормальную проверку
        if (articleRepository.findArticleByArticleNameAndMetalType(article.getArticleName(), article.getMetalType()) != null &&
                (!article.getDummyArticleName().equals(articleRepository.findArticleByArticleId(articleId).getDummyArticleName()))) {
            validationResult.addError(new FieldError("article", "articleName",
                    "Артикул " + article.getArticleName() + " уже есть в базе."));
            modelMap.addAttribute("metalTypeList", metalTypeRepository.findAllMetalTypes());
            modelMap.addAttribute("collectionTypesList", CollectionType.values());
            modelMap.addAttribute("insertsList", mineralRepository.findAllMinerals());
            return "addArticle";
        }

        articleServiceImpl.saveArticle(article);
        return "redirect:/article";
    }

    @GetMapping(path = "/delete/{articleId}")
    public String deleteArticle(@PathVariable(name = "articleId") long articleId) {
        articleServiceImpl.deleteArticle(articleId);
        return "redirect:/article";
    }

}
