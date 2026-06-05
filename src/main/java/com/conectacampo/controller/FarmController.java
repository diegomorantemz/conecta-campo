package com.conectacampo.controller;

import com.conectacampo.model.Farm;
import com.conectacampo.model.User;
import com.conectacampo.repository.FarmRepository;
import com.conectacampo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/farmer/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    // Lista de mis fincas
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

    // Formulario para crear finca
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("farm", new Farm());
        model.addAttribute("title", "Nueva Finca");
        return "farmer/farm-form";
    }

    // Guardar finca
    @PostMapping("/save")
    public String save(@ModelAttribute Farm farm,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes) {
        try {
            String email = authentication.getName();
            User farmer = userRepository.findByEmail(email).orElse(null);

            if (farmer != null) {
                farm.setUser(farmer);
                farmRepository.save(farm);
                redirectAttributes.addFlashAttribute("success", "Finca guardada correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/farmer/farms";
    }

    // Formulario para editar finca
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

    // Eliminar finca
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            farmRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Finca eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la finca.");
        }
        return "redirect:/farmer/farms";
    }
}