package org.jewel.db;

import org.jewel.model.Group;
import org.jewel.model.User;
import org.jewel.model.UserRoles;
import org.jewel.model.UserStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "users", itemResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByLogin(String login);

    List<User> findUsersByGroup(Group g);

    @Query("SELECT u from User u")
    List<User> findAllUsers();

    User findUserByLogin(String login);

    @Query("SELECT u from User u WHERE u.status = ?1 or u.status=?2")
    List<User> findActiveUsers(UserStatus userStatus, UserStatus status);

    User findUserById(int id);
}
