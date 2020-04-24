package org.jewel.db;

import org.jewel.model.Priority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityRepository extends PagingAndSortingRepository<Priority, Integer> {
}
