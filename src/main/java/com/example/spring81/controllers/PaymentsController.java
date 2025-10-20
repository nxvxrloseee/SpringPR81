package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.dto.PaymentFormDto;
import com.example.spring81.services.PaymentsService;
import com.example.spring81.services.StatusService;
import com.example.spring81.repositories.UserModelRepository;
import jakarta.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payments")
public class PaymentsController {

    @Autowired private PaymentsService paymentsService;
    @Autowired private UserModelRepository userRepository;
    @Autowired private StatusService statusService;

    private void loadRelatedData(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allStatuses", statusService.findAll());
    }

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentsService.findAll());
        return "payments/list";
    }

    @GetMapping("/new")
    public String showNewPaymentForm(Model model) {
        model.addAttribute("paymentDto", new PaymentFormDto());
        model.addAttribute("pageTitle", "Add New Payment");
        loadRelatedData(model);
        return "payments/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditPaymentForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("paymentDto", paymentsService.getPaymentFormDtoById(id));
            model.addAttribute("pageTitle", "Edit Payment (ID: " + id + ")");
            loadRelatedData(model);
            return "payments/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/payments";
        }
    }

    @PostMapping("/save")
    public String savePayment(
            @Valid @ModelAttribute("paymentDto") PaymentFormDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add New Payment");
            loadRelatedData(model);
            return "payments/form";
        }

        try {
            paymentsService.saveFromDto(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Payment created successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create payment: " + e.getMessage());
            return "redirect:/payments/new";
        }
        return "redirect:/payments";
    }

    @PostMapping("/update/{id}")
    public String updatePayment(
            @PathVariable Long id,
            @Valid @ModelAttribute("paymentDto") PaymentFormDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Payment (ID: " + id + ")");
            loadRelatedData(model);
            return "payments/form";
        }

        try {
            paymentsService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Payment updated successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update payment: " + e.getMessage());
            return "redirect:/payments/edit/" + id;
        }
        return "redirect:/payments";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            paymentsService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Payment deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/payments";
    }
}