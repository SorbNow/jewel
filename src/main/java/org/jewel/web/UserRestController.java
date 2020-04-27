/*
package org.jewel.web;

import org.jewel.db.UserRepository;
import org.jewel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users")
    public List<User> findUser() {
        ArrayList<User> allUsers = new ArrayList<>();
        for (User user:userRepository.findAll()) {
            allUsers.add(user);
        }
        return allUsers;
    }
}
*/
