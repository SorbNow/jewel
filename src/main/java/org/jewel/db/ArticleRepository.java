package org.jewel.db;

import org.jewel.model.Article;
import org.jewel.model.CollectionType;
import org.jewel.model.MetalType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Integer> {
    List<Article> findArticlesByMetalType(MetalType metalType);

    List<Article> findArticlesByArticle(String articleName);

    List<Article> findArticlesByCollectionType(CollectionType collectionType);

    @Query("SELECT a from Article a WHERE a.averageWeight < :maxWeight AND a.averageWeight > :minWeight")
    List<Article> findArticlesByAverageWeightIsBetween(double minWeight, double maxWeight);

}
