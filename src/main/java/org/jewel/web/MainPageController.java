package org.jewel.web;

import org.jewel.db.UserRepository;
import org.jewel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainPageController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/")
    public String mainPage(Principal principal, ModelMap model) {
        if (principal != null) {
            User user = userRepository.findUserByLogin(principal.getName());
            String password = encoder.encode("1111");
            if (encoder.matches("1111", user.getEncodedPassword())) {
                return "redirect:/set-password";
            }
        } else {
            return "redirect:/login-page";
        }
        return "redirect:/home";
    }
}
