package org.jewel.repos;

import org.jewel.model.Priority;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "priorities", itemResourceRel = "priorities", path = "priorities")
public interface PriorityRepository extends PagingAndSortingRepository<Priority, Integer> {

    Priority findPriorityByPriorityType(String type);

    Priority findPriorityById(int id);
}
