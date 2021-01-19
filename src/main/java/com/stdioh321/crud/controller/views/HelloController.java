package com.stdioh321.crud.controller.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController {

    @GetMapping("home")
    public String get(Model model){
        model.addAttribute("title", "Super title from CONTROLLER");
        return "home";
    }
}