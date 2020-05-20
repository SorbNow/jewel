package org.jewel.db;

import org.jewel.model.Article;
import org.jewel.model.ArticleInOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "articlesInOrders", itemResourceRel = "articlesInOrders", path = "articlesInOrders")
public interface ArticleInOrderRepository extends PagingAndSortingRepository<ArticleInOrder, Long> {

    @Query("SELECT a from ArticleInOrder a")
    List<ArticleInOrder> findAllArticlesInOrders();

    List<ArticleInOrder> findArticleInOrdersByArticleOrder(String articleOrder);

    ArticleInOrder findArticleInOrderByArticleAndArticleOrder(Article article, String articleOrder);

}
