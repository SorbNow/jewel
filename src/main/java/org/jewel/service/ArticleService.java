package org.jewel.service;

import org.jewel.model.Article;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface ArticleService {
    void saveArticle(Article article);
    List<String> getCollectionTypesList();
    Article prepareArticleBeforeSaving(Article article);
    ModelMap fillModel(ModelMap modelMap);
    void deleteArticle(long id);
    List<Article> getArticleList();

}
