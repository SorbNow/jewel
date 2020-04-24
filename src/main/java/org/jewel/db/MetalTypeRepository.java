package org.jewel.db;

import org.jewel.model.MetalType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetalTypeRepository extends PagingAndSortingRepository<MetalType, Integer> {
    MetalType findMetalTypeByMetalTypeNameAndHallmark(String name, int hallmark);

    @Query("SELECT m FROM MetalType m")
    List<MetalType> findAllMetalTypes();
}
