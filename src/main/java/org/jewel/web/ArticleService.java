package org.jewel.web;

import org.jewel.db.ArticleRepository;
import org.jewel.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public void saveArticle(Article article) {
    articleRepository.save(article);
    }
}
