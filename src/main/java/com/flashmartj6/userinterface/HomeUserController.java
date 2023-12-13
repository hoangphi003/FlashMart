package com.flashmartj6.userinterface;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeUserController {
    @GetMapping({"/","","/index","/home"})
    public String index(Model model){
        return "/views/user/index.html";
    }
}