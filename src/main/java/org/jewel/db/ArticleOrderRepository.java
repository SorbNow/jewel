package org.jewel.db;

import org.jewel.model.ArticleOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "orders", itemResourceRel = "orders", path = "orders")
public interface ArticleOrderRepository extends PagingAndSortingRepository<ArticleOrder, Integer> {

    @Query("SELECT o from ArticleOrder o")
    List<ArticleOrder> findAllOrders();

}
