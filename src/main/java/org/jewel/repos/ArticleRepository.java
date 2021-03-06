package org.jewel.repos;

import org.jewel.model.Article;
import org.jewel.model.CollectionType;
import org.jewel.model.MetalType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "articles", itemResourceRel = "articles", path = "articles")
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    List<Article> findArticlesByMetalType(MetalType metalType);

    List<Article> findArticlesByArticleName(String articleName);

    List<Article> findArticlesByCollectionType(CollectionType collectionType);

    @Query("SELECT a from Article a WHERE a.averageWeight < :maxWeight AND a.averageWeight > :minWeight")
    List<Article> findArticlesByAverageWeightIsBetween(double minWeight, double maxWeight);

    @Query("SELECT a from Article a")
    List<Article> findAllArticles();

    @Query("SELECT a from Article a ORDER BY a.articleId")
    List<Article> findAllArticlesSorted();

    Article findArticleByArticleNameAndMetalType(String article, MetalType metalType);

    Article findArticleByArticleId(long articleId);

}
