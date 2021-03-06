package org.jewel.controller;

import org.jewel.repos.UserRepository;
import org.jewel.model.User;
import org.jewel.model.UserStatus;
import org.jewel.utils.LoginForm;
import org.jewel.utils.LoginFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes("loginForm")
public class LoginController {
    @Autowired
    private UserRepository users;

    @ModelAttribute("loginForm")
    public LoginForm createForm() {
        List<User> userList = users.findAllUsers();
        LoginForm form = new LoginForm();
        form.setSelectedUserName(userList.get(0).getLogin());
        form.setPassword("");
        return form;
    }

    public LoginFormData createData() {
        LoginFormData loginForm = new LoginFormData();
        loginForm.setUsers(users.findActiveUsers(UserStatus.ACTIVE, UserStatus.REGISTERED));
        return loginForm;
    }

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping(path = "/login-page")
    public String loginPage(Principal principal,
                            ModelMap model,
                            @ModelAttribute("loginForm") LoginForm form) {

        if (principal != null) {
            return "redirect:/";
        }
        List<User> userList = users.findAllUsers();
        model.addAttribute("loginData", createData());
        return "loginPage";
    }

    // @PostMapping(path = "/login-page")
    public String processLogin(ModelMap model,
                               @Validated
                               @ModelAttribute("loginForm")
                                       LoginForm form,
                               BindingResult validationResult
    ) {
        model.addAttribute("loginData", createData());
        if (model.getAttribute("loggedUsername") != null) {
            return "redirect:/";
        }

        User user = users.findUserByLogin(form.getSelectedUserName());
        String loggedUserName = form.getSelectedUserName();
        // String loggedGroupName = user.getGroup().getName();


        if (user != null && encoder.matches(form.getPassword(), user.getEncodedPassword())) {
            model.addAttribute("loggedUsername", loggedUserName);
            Date loginDate = new Date();
            System.out.println("loginDate : " + loginDate);
            user.setLastLogin(loginDate);
            user.setStatus(UserStatus.ACTIVE);
            users.save(user);
            return "redirect:/home";
        } else {
            validationResult.addError(new FieldError("form", "password", "Wrong username or password"));
            //return "redirect:/login?login=" + form.getSelectedUser();
            return "login";
        }
    }
}
