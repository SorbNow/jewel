package org.jewel.db;

import org.jewel.model.Group;
import org.jewel.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByLogin(String login);

    List<User> findUsersByGroup(Group g);

    @Query("SELECT u from User u")
    List<User> findAllUsers();

    User findUserByLogin(String login);
}
