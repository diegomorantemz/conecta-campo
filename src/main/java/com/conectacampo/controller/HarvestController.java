package com.conectacampo.controller;

import com.conectacampo.model.Harvest;
import com.conectacampo.model.enums.HarvestStatus;
import com.conectacampo.repository.HarvestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/harvests")
@RequiredArgsConstructor
public class HarvestController {

    private final HarvestRepository harvestRepository;

    @GetMapping
    public String listHarvests(Model model) {
        List<Harvest> activeHarvests = harvestRepository.findByStatus(HarvestStatus.ACTIVE);
        model.addAttribute("title", "Cosechas Disponibles");
        model.addAttribute("currentPage", "harvests");
        model.addAttribute("harvests", activeHarvests);
        return "harvests";
    }

    @GetMapping("/{id}")
    public String harvestDetail(@PathVariable Long id, Model model) {
        Harvest harvest = harvestRepository.findById(id).orElse(null);
        model.addAttribute("title", "Detalle de Cosecha");
        model.addAttribute("currentPage", "harvests");
        model.addAttribute("harvest", harvest);
        return "harvest-detail";
    }
}