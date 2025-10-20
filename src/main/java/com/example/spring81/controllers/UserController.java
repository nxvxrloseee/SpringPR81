package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.dto.UserFormDto;
import com.example.spring81.services.RolesService;
import com.example.spring81.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private RolesService rolesService;

    // Общий метод для загрузки ролей в Model (для форм)
    private void loadRelatedData(Model model) {
        model.addAttribute("allRoles", rolesService.findAll());
    }

    // R: Read (All)
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("userDto", new UserFormDto());
        model.addAttribute("pageTitle", "Add New User");
        loadRelatedData(model);
        return "users/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserFormDto dto = userService.getFormDtoById(id);
            model.addAttribute("userDto", dto);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            loadRelatedData(model);
            return "users/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users";
        }
    }

    // C: Save (Process create form)
    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("userDto") UserFormDto dto,
                           BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            bindingResult.rejectValue("password", "NotBlank", "Password cannot be blank for new user");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add New User");
            loadRelatedData(model);
            return "users/form";
        }

        try {
            userService.adminSaveFromDto(dto);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username or Email already exists.");
            return "redirect:/users/new";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/new";
        }
        return "redirect:/users";
    }

    // U: Save (Process update form)
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute("userDto") UserFormDto dto,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            loadRelatedData(model);
            return "users/form";
        }

        try {
            userService.adminUpdateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username or Email already exists.");
            return "redirect:/users/edit/" + id;
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/edit/" + id;
        }
        return "redirect:/users";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }
}