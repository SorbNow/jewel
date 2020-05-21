package org.jewel.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainPageController {
    @GetMapping("/")
    public String mainPage(Principal principal, ModelMap model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            return "redirect:/login-page";
        }
        return "redirect:/home";
    }
}
