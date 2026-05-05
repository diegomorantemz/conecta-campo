package com.conectacampo.controller;

import com.conectacampo.model.Harvest;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.HarvestStatus;
import com.conectacampo.model.enums.Role;
import com.conectacampo.repository.FairRepository;
import com.conectacampo.repository.HarvestRepository;
import com.conectacampo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final HarvestRepository harvestRepository;
    private final FairRepository fairRepository;

    // Dashboard para agricultor (datos de prueba con usuario fijo)
    @GetMapping("/farmer")
    public String farmerDashboard(Model model) {
        // Temporal: obtener un agricultor de ejemplo (id=2 o el primero que encuentre)
        User farmer = userRepository.findByRole(Role.FARMER).stream().findFirst().orElse(null);

        if (farmer != null) {
            // Obtener cosechas del agricultor
            List<Harvest> myHarvests = harvestRepository.findByFarmUserId(farmer.getId());
            long activeHarvests = myHarvests.stream().filter(h -> h.getStatus() == HarvestStatus.ACTIVE).count();
            long totalHarvests = myHarvests.size();
            long myFairs = fairRepository.findByFarmer(farmer).size();

            model.addAttribute("farmerName", farmer.getName() + " " + farmer.getLastname());
            model.addAttribute("activeHarvests", activeHarvests);
            model.addAttribute("totalHarvests", totalHarvests);
            model.addAttribute("myFairs", myFairs);
            model.addAttribute("recentHarvests", myHarvests.stream().limit(5).toList());
        }

        model.addAttribute("title", "Panel del Agricultor");
        model.addAttribute("currentPage", "farmer-dashboard");
        return "farmer-dashboard";
    }

    // Dashboard para comprador (datos de prueba)
    @GetMapping("/buyer")
    public String buyerDashboard(Model model) {
        // Temporal: obtener un comprador de ejemplo o crear uno de prueba
        User buyer = userRepository.findByRole(Role.BUYER).stream().findFirst().orElse(null);

        if (buyer == null) {
            // Crear un comprador de prueba si no existe
            buyer = new User();
            buyer.setName("Cliente");
            buyer.setLastname("Prueba");
            buyer.setEmail("cliente@test.com");
            buyer.setRole(Role.BUYER);
        }

        long totalFairsNearby = fairRepository.count(); // Temporal: todas las ferias
        long recentContacts = 0; // Temporal, luego se conecta con ContactRepository

        model.addAttribute("buyerName", buyer.getName() + " " + buyer.getLastname());
        model.addAttribute("totalFairsNearby", totalFairsNearby);
        model.addAttribute("recentContacts", recentContacts);
        model.addAttribute("title", "Panel del Comprador");
        model.addAttribute("currentPage", "buyer-dashboard");
        return "buyer-dashboard";
    }
}