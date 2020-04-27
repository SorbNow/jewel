package org.jewel.db;

import org.jewel.model.Mineral;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "minerals", itemResourceRel = "minerals", path = "minerals")
public interface MineralRepository extends PagingAndSortingRepository<Mineral, Integer> {
    Mineral findMineralByInsert(String name);
}
