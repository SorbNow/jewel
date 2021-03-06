package org.jewel.repos;

import org.jewel.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "customers", itemResourceRel = "customers", path = "customers")
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
    Customer findCustomerByCustomerName(String customerName);

    Customer findCustomerById(int id);
}
