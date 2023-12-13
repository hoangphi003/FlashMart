package com.flashmartj6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginAdminController {

    @RequestMapping("/auth/login")
    public String showLoginForm() {
        return "views/security/loginauth";
    }
    @RequestMapping("/auth/login/error")
    public String LoginFormError(Model model){
        model.addAttribute("message","Invalid username or password");
         return "views/security/loginauth"; 
    }
    @RequestMapping("/auth/login/accesspage")
    public String LoginAccessPage(Model model){
        model.addAttribute("message","Access denied");
         return "views/security/loginauth"; 
    }
}
