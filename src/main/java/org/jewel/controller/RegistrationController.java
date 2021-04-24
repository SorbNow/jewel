package org.jewel.controller;

import org.jewel.repos.UserRepository;
import org.jewel.model.User;
import org.jewel.model.UserRoles;
import org.jewel.model.UserStatus;
import org.jewel.utils.RegistrationForm;
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
import java.util.Date;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository users;

    @Autowired
    private PasswordEncoder encoder;

    @ModelAttribute("form")
    public RegistrationForm createForm() {
        RegistrationForm form = new RegistrationForm();
        form.setLogin("");
        form.setPassword("");
        form.setRole("");

        return form;
    }

    private List<String> getListOfRoles() {
        List<String> roles = new ArrayList<>();
        for (UserRoles userRoles : UserRoles.values()) {
            roles.add(userRoles.name());
        }
        return roles;
    }

    @GetMapping(path = "/admin/users/add")
    public String getRegistrationForm(ModelMap model,
                                      @ModelAttribute("form") RegistrationForm form) {
        List<String> roles = getListOfRoles();
        User user = new User();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);

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
        model.addAttribute("roles", getListOfRoles());

        if (validationResult.hasErrors()) {
            return "register";
        }

        try {
            User u = new User();
            u.setLogin(form.getLogin());
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

        return "redirect:/admin/users";
    }
}
