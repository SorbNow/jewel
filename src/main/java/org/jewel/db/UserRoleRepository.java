package org.jewel.db;

import org.jewel.model.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "roles", itemResourceRel = "roles", path = "roles")
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole,Integer> {
    UserRole findByRoleName(String name);
}
