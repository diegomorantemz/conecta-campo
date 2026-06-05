package com.conectacampo.controller;

import com.conectacampo.model.Farm;
import com.conectacampo.model.Harvest;
import com.conectacampo.model.Product;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.HarvestStatus;
import com.conectacampo.repository.FarmRepository;
import com.conectacampo.repository.HarvestRepository;
import com.conectacampo.repository.ProductRepository;
import com.conectacampo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/farmer/harvests")
@RequiredArgsConstructor
public class FarmerHarvestController {

    private final HarvestRepository harvestRepository;
    private final ProductRepository productRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    // Lista de mis cosechas
    @GetMapping
    public String myHarvests(Model model, Authentication authentication) {
        String email = authentication.getName();
        User farmer = userRepository.findByEmail(email).orElse(null);

        if (farmer != null) {
            List<Harvest> myHarvests = harvestRepository.findByFarmUserId(farmer.getId());
            model.addAttribute("harvests", myHarvests);
        } else {
            model.addAttribute("harvests", List.of());
        }

        model.addAttribute("title", "Mis Cosechas");
        model.addAttribute("currentPage", "farmer-harvests");
        return "farmer/harvests";
    }

    // Formulario para crear cosecha
    @GetMapping("/create")
    public String createForm(Model model, Authentication authentication) {
        String email = authentication.getName();
        User farmer = userRepository.findByEmail(email).orElse(null);

        List<Product> products = productRepository.findAll();
        Harvest harvest = new Harvest();

        if (farmer != null) {
            // ✅ Obtener TODAS las fincas del agricultor
            List<Farm> farms = farmRepository.findByUser(farmer);
            model.addAttribute("farms", farms);

            // ✅ Verificar si tiene fincas
            if (farms.isEmpty()) {
                model.addAttribute("noFarms", true);
            } else if (farms.size() == 1) {
                // Si solo tiene una finca, seleccionarla automáticamente
                harvest.setFarm(farms.get(0));
            }
        }

        model.addAttribute("harvest", harvest);
        model.addAttribute("products", products);
        model.addAttribute("title", "Nueva Cosecha");
        return "farmer/harvest-form";
    }

    // Guardar cosecha (crear o actualizar)
    @PostMapping("/save")
    public String save(@ModelAttribute Harvest harvest,
                       Authentication authentication,
                       RedirectAttributes redirectAttributes) {
        try {
            String email = authentication.getName();
            User farmer = userRepository.findByEmail(email).orElse(null);

            if (farmer != null) {
                // ✅ Validar que la finca existe y pertenece al agricultor
                if (harvest.getFarm() == null || harvest.getFarm().getId() == null) {
                    redirectAttributes.addFlashAttribute("error", "Debes seleccionar una finca.");
                    return "redirect:/farmer/harvests/create";
                }

                Farm selectedFarm = farmRepository.findById(harvest.getFarm().getId()).orElse(null);
                if (selectedFarm == null || !selectedFarm.getUser().getId().equals(farmer.getId())) {
                    redirectAttributes.addFlashAttribute("error", "Finca no válida.");
                    return "redirect:/farmer/harvests/create";
                }

                harvest.setFarm(selectedFarm);
                harvest.setStatus(HarvestStatus.ACTIVE);
                harvestRepository.save(harvest);
                redirectAttributes.addFlashAttribute("success", "Cosecha guardada correctamente.");
            } else {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        return "redirect:/farmer/harvests";
    }

    // Formulario para editar
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Harvest harvest = harvestRepository.findById(id).orElse(null);
        if (harvest == null) {
            redirectAttributes.addFlashAttribute("error", "Cosecha no encontrada.");
            return "redirect:/farmer/harvests";
        }

        List<Product> products = productRepository.findAll();

        // ✅ Obtener las fincas del agricultor para el selector
        if (harvest.getFarm() != null && harvest.getFarm().getUser() != null) {
            List<Farm> farms = farmRepository.findByUser(harvest.getFarm().getUser());
            model.addAttribute("farms", farms);
        }

        model.addAttribute("harvest", harvest);
        model.addAttribute("products", products);
        model.addAttribute("title", "Editar Cosecha");
        return "farmer/harvest-form";
    }

    // Eliminar cosecha
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            harvestRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Cosecha eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la cosecha.");
        }
        return "redirect:/farmer/harvests";
    }
}