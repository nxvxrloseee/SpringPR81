package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Roles;
import com.example.spring81.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    // R: Read (All)
    @GetMapping
    public String listRoles(Model model) {
        model.addAttribute("roles", rolesService.findAll());
        return "roles/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewRoleForm(Model model) {
        model.addAttribute("role", new Roles());
        model.addAttribute("pageTitle", "Add New Role");
        return "roles/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditRoleForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Roles role = rolesService.findById(id);
            model.addAttribute("role", role);
            model.addAttribute("pageTitle", "Edit Role (ID: " + id + ")");
            return "roles/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/roles";
        }
    }

    // C + U: Save (Process form)
    @PostMapping("/save")
    public String saveRole(@ModelAttribute("role") Roles role, RedirectAttributes redirectAttributes) {
        rolesService.save(role);
        redirectAttributes.addFlashAttribute("successMessage", "Role saved successfully!");
        return "redirect:/roles";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            rolesService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Role deleted successfully!");
        } catch (ResourceNotFoundException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/roles";
    }
}