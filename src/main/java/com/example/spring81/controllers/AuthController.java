package com.example.spring81.controllers;

import com.example.spring81.models.UserModel;
import com.example.spring81.repositories.RolesRepository;
import com.example.spring81.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RolesRepository rolesRepo;

    public AuthController(UserService userService, RolesRepository rolesRepo) {
        this.userService = userService;
        this.rolesRepo = rolesRepo;
    }

    // === LOGIN PAGE ===
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
    // === REGISTER PAGE ===
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserModel());
        model.addAttribute("roles", rolesRepo.findAll());
        return "auth/register";
    }
    // === REGISTER FORM SUBMIT ===
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserModel user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", rolesRepo.findAll());
            return "auth/register";
        }
        userService.registerUser(user);
        return "redirect:/auth/login?registered=true";
    }
}
