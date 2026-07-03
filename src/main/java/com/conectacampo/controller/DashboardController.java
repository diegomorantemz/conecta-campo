package com.conectacampo.controller;

import com.conectacampo.model.Harvest;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.HarvestStatus;
import com.conectacampo.model.enums.Role;
import com.conectacampo.repository.FairRepository;
import com.conectacampo.repository.HarvestRepository;
import com.conectacampo.repository.UserRepository;
import com.conectacampo.model.enums.FairStatus;
import com.conectacampo.model.enums.HarvestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;
    private final HarvestRepository harvestRepository;
    private final FairRepository fairRepository;

    @GetMapping("/farmer")
    public String farmerDashboard(Model model, Authentication authentication) {

        String email = authentication.getName();
        User farmer = userRepository.findByEmail(email).orElse(null);

        if (farmer != null) {

            List<Harvest> myHarvests = harvestRepository.findByFarmUserId(farmer.getId());
            long activeHarvests = myHarvests.stream().filter(h -> h.getStatus() == HarvestStatus.ACTIVE).count();
            long totalHarvests = myHarvests.size();
            long myFairs = fairRepository.findByFarmer(farmer).size();

            model.addAttribute("farmerName", farmer.getName() + " " + farmer.getLastname());
            model.addAttribute("activeHarvests", activeHarvests);
            model.addAttribute("totalHarvests", totalHarvests);
            model.addAttribute("myFairs", myFairs);
            model.addAttribute("recentHarvests", myHarvests.stream().limit(5).toList());
        } else {
            model.addAttribute("farmerName", "Agricultor");
            model.addAttribute("activeHarvests", 0);
            model.addAttribute("totalHarvests", 0);
            model.addAttribute("myFairs", 0);
            model.addAttribute("recentHarvests", List.of());
        }

        model.addAttribute("title", "Panel del Agricultor");
        model.addAttribute("currentPage", "farmer-dashboard");
        return "farmer-dashboard";
    }

    @GetMapping("/buyer")
    public String buyerDashboard(Model model, Authentication authentication) {
        // Obtener el email del usuario autenticado
        String email = authentication.getName();
        User buyer = userRepository.findByEmail(email).orElse(null);

        String buyerName = "Comprador";
        if (buyer != null) {
            buyerName = buyer.getName() + " " + buyer.getLastname();
        }

        long totalHarvests = harvestRepository.count();
        long activeHarvests = harvestRepository.findByStatus(HarvestStatus.ACTIVE).size();
        long totalFairs = fairRepository.count();
        long upcomingFairs = fairRepository.findByStatus(FairStatus.UPCOMING).size();

        model.addAttribute("buyerName", buyerName);
        model.addAttribute("totalHarvests", totalHarvests);
        model.addAttribute("activeHarvests", activeHarvests);
        model.addAttribute("totalFairs", totalFairs);
        model.addAttribute("upcomingFairs", upcomingFairs);
        model.addAttribute("title", "Panel del Comprador");
        model.addAttribute("currentPage", "buyer-dashboard");
        return "buyer-dashboard";
    }
}