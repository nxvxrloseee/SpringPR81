package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.dto.PlansFormDto;
import com.example.spring81.services.PlansService;
import com.example.spring81.services.StatusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/plans")
public class PlansController {

    @Autowired private PlansService plansService;
    @Autowired private StatusService statusService;


    private void loadRelatedData(Model model) {
        model.addAttribute("allStatuses", statusService.findAll());
    }

    // R: Read (All)
    @GetMapping
    public String listPlans(Model model) {
        model.addAttribute("plans", plansService.findAll());
        return "plans/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewPlanForm(Model model) {
        model.addAttribute("planDto", new PlansFormDto());
        model.addAttribute("pageTitle", "Add New Plan");
        loadRelatedData(model);
        return "plans/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditPlanForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            PlansFormDto dto = plansService.getPlanFormDtoById(id);
            model.addAttribute("planDto", dto);
            model.addAttribute("pageTitle", "Edit Plan (ID: " + id + ")");
            loadRelatedData(model);
            return "plans/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/plans";
        }
    }

    // C: Save (Process create form)
    @PostMapping("/save")
    public String savePlan(
            @Valid @ModelAttribute("planDto") PlansFormDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Plan");
            loadRelatedData(model);
            return "plans/form";
        }

        try {
            plansService.saveFromDto(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Plan created successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/plans/new";
        }
        return "redirect:/plans";
    }

    // U: Save (Process update form)
    @PostMapping("/update/{id}")
    public String updatePlan(
            @PathVariable Long id,
            @Valid @ModelAttribute("planDto") PlansFormDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Plan (ID: " + id + ")");
            loadRelatedData(model);
            return "plans/form";
        }

        try {
            plansService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Plan updated successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/plans/edit/" + id;
        }
        return "redirect:/plans";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deletePlan(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            plansService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Plan deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/plans";
    }
}