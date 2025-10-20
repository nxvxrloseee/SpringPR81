package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.dto.ServerFormDto;
import com.example.spring81.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servers")
public class ServerController {

    @Autowired private ServerService serverService;
    @Autowired private UserService userService;
    @Autowired private StatusService statusService;
    @Autowired private OSService osService;
    @Autowired private PlansService plansService;


    private void loadRelatedData(Model model) {
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allStatuses", statusService.findAll());
        model.addAttribute("allOS", osService.findAll());
        model.addAttribute("allPlans", plansService.findAll());
    }

    // R: Read (All)
    @GetMapping
    public String listServers(Model model) {
        model.addAttribute("servers", serverService.findAll());
        return "servers/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewServerForm(Model model) {
        model.addAttribute("serverDto", new ServerFormDto());
        model.addAttribute("pageTitle", "Add New Server");
        loadRelatedData(model);
        return "servers/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditServerForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ServerFormDto dto = serverService.getFormDtoById(id);
            model.addAttribute("serverDto", dto);
            model.addAttribute("pageTitle", "Edit Server (ID: " + id + ")");
            loadRelatedData(model);
            return "servers/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/servers";
        }
    }

    // C + U: Save (Process form)
    @PostMapping({"/save", "/update/{id}"})
    public String saveOrUpdateServer(@PathVariable(required = false) Long id,
                                     @Valid @ModelAttribute("serverDto") ServerFormDto dto,
                                     BindingResult bindingResult,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", (id == null ? "Add New Server" : "Edit Server (ID: " + id + ")"));
            loadRelatedData(model);
            return "servers/form";
        }

        try {
            if (id == null) {
                serverService.saveFromDto(dto);
                redirectAttributes.addFlashAttribute("successMessage", "Server created successfully!");
            } else {
                serverService.updateFromDto(id, dto);
                redirectAttributes.addFlashAttribute("successMessage", "Server updated successfully!");
            }
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/servers";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deleteServer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            serverService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Server deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/servers";
    }
}