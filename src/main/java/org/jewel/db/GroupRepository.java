package org.jewel.db;

import org.jewel.model.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "groups", itemResourceRel = "groups", path = "groups")
public interface GroupRepository extends PagingAndSortingRepository<Group, Integer> {
    Group findGroupByName(String name);

    @Query("SELECT g FROM Group g")
    List<Group> findAllGroups();

}
