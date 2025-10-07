package org.example.group34.controller;

import jakarta.validation.Valid;
import org.example.group34.model.User;
import org.example.group34.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("duplicate","Username already exists.");
            return "register";
        }
        userService.registerUser(user.getUsername(), user.getPassword());
        return "redirect:/login";
    }
}