package org.jewel.web;

import org.jewel.db.GroupRepository;
import org.jewel.db.UserRepository;
import org.jewel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserViewController {

    @Autowired
    private UserRepository users;

    @Autowired
    private GroupRepository groupRepository;


    @GetMapping(path = "/admin/users")
    public String getUserList(ModelMap model) {
        List<User> userList = users.findAllUsers();
        model.addAttribute("userList", userList);
        return "userList";
    }
}
