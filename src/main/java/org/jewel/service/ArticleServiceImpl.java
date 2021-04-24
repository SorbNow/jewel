package org.jewel.service;

import org.jewel.model.CollectionType;
import org.jewel.repos.ArticleRepository;
import org.jewel.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public List<String> getCollectionTypesList() {
        List<String> collectionTypes = new ArrayList<>();
        for (CollectionType collectionType : CollectionType.values()) {
            collectionTypes.add(collectionType.name());
        }
        return collectionTypes;
    }

    @Override
    public Article prepareArticleBeforeSaving(Article article) {
        article.setArticleName(article.getArticleName().trim());
        article.setDummyArticleName(article.getArticleName() + article.getMetalType().getMetalTypeName() + article.getMetalType().getHallmark());
        return article;
    }

    @Override
    public ModelMap fillModel(ModelMap modelMap) {
        return null;
    }

    @Override
    public void deleteArticle(long id) {
        Article article = articleRepository.findArticleByArticleId(id);
        articleRepository.delete(article);
    }

    @Override
    public List<Article> getArticleList() {
        return articleRepository.findAllArticlesSorted();
    }
}
