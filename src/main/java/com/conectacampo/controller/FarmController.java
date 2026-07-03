package com.conectacampo.controller;

import com.conectacampo.model.Farm;
import com.conectacampo.model.User;
import com.conectacampo.repository.FarmRepository;
import com.conectacampo.repository.UserRepository;
import com.conectacampo.service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/farmer/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final FarmService farmService;

    @GetMapping
    public String myFarms(Model model, Authentication authentication) {
        String email = authentication.getName();
        User farmer = userRepository.findByEmail(email).orElse(null);

        if (farmer != null) {
            List<Farm> myFarms = farmRepository.findByUser(farmer);
            model.addAttribute("farms", myFarms);
        } else {
            model.addAttribute("farms", List.of());
        }

        model.addAttribute("title", "Mis Fincas");
        model.addAttribute("currentPage", "farmer-farms");
        return "farmer/farms";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        if (!model.containsAttribute("farm")) {
            model.addAttribute("farm", new Farm());
        }
        model.addAttribute("title", "Nueva Finca");
        return "farmer/farm-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("farm") Farm farm,
                       BindingResult result,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes,
                       Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "farmer/farm-form";
        }

        try {
            String email = authentication.getName();
            User farmer = userRepository.findByEmail(email).orElse(null);

            if (farmer != null) {
                farmService.saveFarm(farm, farmer);
                redirectAttributes.addFlashAttribute("success", "Finca guardada correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/farmer/farms/create";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/farmer/farms/create";
        }
        return "redirect:/farmer/farms";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Farm farm = farmRepository.findById(id).orElse(null);
        if (farm == null) {
            redirectAttributes.addFlashAttribute("error", "Finca no encontrada.");
            return "redirect:/farmer/farms";
        }

        model.addAttribute("farm", farm);
        model.addAttribute("title", "Editar Finca");
        return "farmer/farm-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("farm") Farm farm,
                         BindingResult result,
                         Authentication authentication,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "farmer/farm-form";
        }

        try {
            String email = authentication.getName();
            User farmer = userRepository.findByEmail(email).orElse(null);

            if (farmer != null) {
                farm.setId(id);
                farmService.updateFarm(id, farm, farmer);
                redirectAttributes.addFlashAttribute("success", "Finca actualizada correctamente.");
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/farmer/farms/edit/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
            return "redirect:/farmer/farms/edit/" + id;
        }
        return "redirect:/farmer/farms";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            farmService.deleteFarm(id);
            redirectAttributes.addFlashAttribute("success", "Finca eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/farmer/farms";
    }
}