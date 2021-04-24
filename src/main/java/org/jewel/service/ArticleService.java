package org.jewel.service;

import org.jewel.repos.ArticleRepository;
import org.jewel.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void saveArticle(Article article) {
    articleRepository.save(article);
    }
}
