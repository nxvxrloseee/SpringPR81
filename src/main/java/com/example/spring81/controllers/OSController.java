package com.example.spring81.controllers;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.OS;
import com.example.spring81.services.OSService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/os")
public class OSController {

    @Autowired
    private OSService osService;

    // R: Read (All)
    @GetMapping
    public String listOS(Model model) {
        model.addAttribute("osList", osService.findAll());
        return "os/list";
    }

    // C: Create (Show form)
    @GetMapping("/new")
    public String showNewOSForm(Model model) {
        model.addAttribute("os", new OS());
        model.addAttribute("pageTitle", "Добавить новую ОС");
        return "os/form";
    }

    // U: Update (Show form)
    @GetMapping("/edit/{id}")
    public String showEditOSForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("os", osService.findById(id));
            model.addAttribute("pageTitle", "Редактировать ОС (ID: " + id + ")");
            return "os/form";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка: " + e.getMessage());
            return "redirect:/os";
        }
    }

    // C + U: Save (Process form)
    @PostMapping("/save")
    public String saveOS(@Valid @ModelAttribute("os") OS os,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {


        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (os.getOsId() == null ? "Добавить новую ОС" : "Редактировать ОС (ID: " + os.getOsId() + ")"));
            return "os/form";
        }

        try {
            osService.save(os);
            String message = (os.getOsId() == null)
                    ? "Операционная система успешно создана!"
                    : "Операционная система успешно обновлена!";
            redirectAttributes.addFlashAttribute("successMessage", message); // <-- ПЕРЕВОД
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось сохранить ОС: " + e.getMessage()); // <-- ПЕРЕВОД
        }
        return "redirect:/os";
    }

    // D: Delete
    @GetMapping("/delete/{id}")
    public String deleteOS(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            osService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Операционная система успешно удалена!"); // <-- ПЕРЕВОД
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить ОС: " + e.getMessage()); // <-- ПЕРЕВОД
        }
        return "redirect:/os";
    }
}