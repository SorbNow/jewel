package org.jewel.web;

import org.jewel.db.GroupRepository;
import org.jewel.db.UserRepository;
import org.jewel.db.UserRoleRepository;
import org.jewel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository users;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @ModelAttribute("form")
    public RegistrationForm createForm() {
        List<Group> groupList = groupRepository.findAllGroups();
        RegistrationForm form = new RegistrationForm();
        form.setLogin("");
        form.setPassword("");
        form.setSelectedGroupName(groupList.get(0).getName());
        form.setRole("");

        return form;
    }

    private List<String> getListOfRoles() {
        List<String> roles = new ArrayList<>();
        for (UserRoles userRoles: UserRoles.values()) {
            roles.add(userRoles.name());
        }
        return roles;
    }

    public RegistrationFormData createData() {
        RegistrationFormData data = new RegistrationFormData();
        data.setGroups(groupRepository.findAllGroups());
        return data;
    }

    @GetMapping(path = "/admin/users/add")
    public String getRegistrationForm(ModelMap model,
                                      @ModelAttribute("form") RegistrationForm form) {
        List<Group> groupList = groupRepository.findAllGroups();
        List<String> roles = getListOfRoles();
        model.addAttribute("data", createData());
        model.addAttribute("roles", roles);
        return "register";
    }

    @PostMapping(path = "/admin/users/add")
    public String processRegistrationForm(
            ModelMap model,
            @Validated
            @ModelAttribute("form")
                    RegistrationForm form,
            BindingResult validationResult
    ) {
        model.addAttribute("data", createData());
        model.addAttribute("roles", getListOfRoles());
        Group group = groupRepository.findGroupByName(form.getSelectedGroupName());
        if (group == null) {
            validationResult.addError(new FieldError("form", "selectedGroupName",
                    "No group " + form.getSelectedGroupName() + "found"));
            return "register";
        }

        if (validationResult.hasErrors()) {
            return "register";
        }

        try {
            User u = new User();
            u.setLogin(form.getLogin());
            u.setGroup(group);
            u.setEncodedPassword(encoder.encode(form.getPassword()));
            u.setLastLogin(new Date(0));
            u.setStatus(UserStatus.REGISTERED);
            u.setUserRole(form.getRole());
            users.save(u);
        } catch (Throwable cause) {
            validationResult.addError(new FieldError("form", "login",
                    "User with login " + form.getLogin() + " is already registered."));
            return "register";
        }

        return "redirect:/login-page";
    }
}
