package org.jewel.controller;

import org.jewel.repos.UserRepository;
import org.jewel.model.User;
import org.jewel.model.UserRoles;
import org.jewel.model.UserStatus;
import org.jewel.utils.ChangePasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    private List<String> getListOfRoles() {
        List<String> roles = new ArrayList<>();
        for (UserRoles userRoles : UserRoles.values()) {
            roles.add(userRoles.name());
        }
        return roles;
    }

    @ModelAttribute("passwordResetForm")
    public ChangePasswordForm changePasswordForm() {
        ChangePasswordForm form = new ChangePasswordForm();
        form.setNewPassword("12");
        form.setConfirmNewPassword("12");
        return form;
    }


    @GetMapping(path = "/admin/users/{id}")
    public String editUser(@PathVariable(name = "id") int id, ModelMap modelMap) {
        User user = userRepository.findUserById(id);
        modelMap.addAttribute("user", user);
        List<String> roles = new ArrayList<>();
        for (UserRoles userRoles : UserRoles.values()) {
            roles.add(userRoles.name());
        }
        modelMap.addAttribute("roles", getListOfRoles());
        return "editUser";
    }

    @PostMapping(path = "/admin/users/{id}")
    public String saveEditedUser(@PathVariable(name = "id") int id,
                           ModelMap modelMap,
                           @Validated
                           @ModelAttribute("user")
                                   User user,
                           BindingResult validationResult) {
        modelMap.addAttribute("roles", getListOfRoles());
        User user1 = userRepository.findUserById(id);
        user1.setLogin(user.getLogin().trim());
        user1.setUserRole(user.getUserRole());
        if (validationResult.hasErrors()) {
            return "editUser";
        }
        if (userRepository.findUserByLogin(user.getLogin()) != null && !user.getLogin().equals(userRepository.findUserById(id).getLogin())) {
            validationResult.addError(new FieldError("user", "login",
                    "Пользователь " + user.getLogin() + " уже есть в базе."));
            return "editUser";
        }
        userRepository.save(user1);
        modelMap.addAttribute("users", userRepository.findAll());

        return "redirect:/admin/users";
    }

    @GetMapping(path = "/admin/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) {
        User user = userRepository.findUserById(id);
        user.setStatus(UserStatus.REMOVED);
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping(path = "/admin/users/{id}/clearPassword")
    public String clearUserPassword(@PathVariable(name = "id") int id) {
        User user = userRepository.findUserById(id);
        user.setEncodedPassword(encoder.encode("1111"));
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping(path = "/set-password")
    public String changePasswordGet(ModelMap modelMap,
                                    @ModelAttribute("passwordResetForm")
                                            ChangePasswordForm changePasswordForm) {
        return "changePassword";
    }

    @PostMapping(path = "/set-password")
    public String changePasswordPost(Principal principal,
                                     @ModelAttribute("passwordResetForm")
                                     @Validated
                                             ChangePasswordForm form,
                                     BindingResult validationResult) {
        if (!form.getNewPassword().equals(form.getConfirmNewPassword())) {
            validationResult.addError(new FieldError("form", "confirmNewPassword",
                    "Пароли должны совпадать"));
            return "changePassword";

        }
        if (validationResult.hasErrors()) {
            return "changePassword";
        }
        User user = userRepository.findUserByLogin(principal.getName());
        user.setEncodedPassword(encoder.encode(form.getNewPassword()));
        userRepository.save(user);


        return "redirect:/";
    }
}
