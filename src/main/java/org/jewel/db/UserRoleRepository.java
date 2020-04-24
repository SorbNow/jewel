package org.jewel.db;

import org.jewel.model.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole,Integer> {
    UserRole findByRoleName(String name);
}
