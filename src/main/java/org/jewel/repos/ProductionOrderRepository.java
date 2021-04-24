package org.jewel.repos;

import org.jewel.model.ProductionOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "orders", itemResourceRel = "orders", path = "orders")
public interface ProductionOrderRepository extends PagingAndSortingRepository<ProductionOrder, Long> {

    @Query("SELECT o from ProductionOrder o")
    List<ProductionOrder> findAllOrders();

    ProductionOrder findProductionOrderByOrderNumber(String number);

    ProductionOrder findProductionOrderByOrderId(long id);

}
