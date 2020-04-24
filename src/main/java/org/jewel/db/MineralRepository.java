package org.jewel.db;

import org.jewel.model.Mineral;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MineralRepository extends PagingAndSortingRepository<Mineral, Integer> {
    Mineral findMineralByInsert(String name);
}
