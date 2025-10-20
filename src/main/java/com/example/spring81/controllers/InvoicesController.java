package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.dto.InvoiceFormDto;
import com.example.spring81.services.InvoicesService;
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
@RequestMapping("/invoices")
public class InvoicesController {

    @Autowired private InvoicesService invoicesService;
    @Autowired private UserModelRepository userRepository;
    @Autowired private StatusService statusService;

    private void loadRelatedData(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allStatuses", statusService.findAll());
    }

    // R: Read (All)
    @GetMapping
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoicesService.findAll());
        return "invoices/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewInvoiceForm(Model model) {
        model.addAttribute("invoiceDto", new InvoiceFormDto());
        model.addAttribute("pageTitle", "Add New Invoice");
        loadRelatedData(model);
        return "invoices/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditInvoiceForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("invoiceDto", invoicesService.getInvoiceFormDtoById(id));
            model.addAttribute("pageTitle", "Edit Invoice (ID: " + id + ")");
            loadRelatedData(model);
            return "invoices/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/invoices";
        }
    }

    // C: Save (Process create form)
    @PostMapping("/save")
    public String saveInvoice(
            @Valid @ModelAttribute("invoiceDto") InvoiceFormDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("pageTitle", "Add New Invoice");
            loadRelatedData(model);
            return "invoices/form";
        }

        try {
            invoicesService.saveFromDto(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Invoice created successfully!");
        } catch (ResourceNotFoundException | IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create invoice: " + e.getMessage());
            return "redirect:/invoices/new";
        }
        return "redirect:/invoices";
    }

    // U: Save (Process update form)
    @PostMapping("/update/{id}")
    public String updateInvoice(
            @PathVariable Long id,
            @Valid @ModelAttribute("invoiceDto") InvoiceFormDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Invoice (ID: " + id + ")");
            loadRelatedData(model);
            return "invoices/form";
        }

        try {
            invoicesService.updateFromDto(id, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Invoice updated successfully!");
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update invoice: " + e.getMessage());
            return "redirect:/invoices/edit/" + id;
        }
        return "redirect:/invoices";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            invoicesService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Invoice deleted successfully!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/invoices";
    }
}