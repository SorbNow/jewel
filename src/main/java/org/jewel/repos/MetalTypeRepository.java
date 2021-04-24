package org.jewel.repos;

import org.jewel.model.MetalType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "metalTypes", itemResourceRel = "metalTypes", path = "metalTypes")
public interface MetalTypeRepository extends PagingAndSortingRepository<MetalType, Integer> {
    MetalType findMetalTypeByMetalTypeNameAndHallmark(String name, int hallmark);

    @Query("SELECT m FROM MetalType m")
    List<MetalType> findAllMetalTypes();

    MetalType findMetalTypeById(int id);
}
