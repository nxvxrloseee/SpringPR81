package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Status;
import com.example.spring81.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    // R: Read (All)
    @GetMapping
    public String listStatuses(Model model) {
        model.addAttribute("statuses", statusService.findAll());
        return "status/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewStatusForm(Model model) {
        model.addAttribute("status", new Status());
        model.addAttribute("pageTitle", "Add New Status");
        return "status/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditStatusForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Status status = statusService.findById(id);
            model.addAttribute("status", status);
            model.addAttribute("pageTitle", "Edit Status (ID: " + id + ")");
            return "status/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/status";
        }
    }

    // C + U: Save (Process form)
    @PostMapping("/save")
    public String saveStatus(@ModelAttribute("status") Status status, RedirectAttributes redirectAttributes) {
        statusService.save(status);
        redirectAttributes.addFlashAttribute("successMessage", "Status saved successfully!");
        return "redirect:/status";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deleteStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            statusService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Status deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/status";
    }
}