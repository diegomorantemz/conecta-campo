package com.conectacampo.controller;

import com.conectacampo.model.Fair;
import com.conectacampo.model.enums.FairStatus;
import com.conectacampo.repository.FairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/fairs")
@RequiredArgsConstructor
public class FairController {

    private final FairRepository fairRepository;

    @GetMapping
    public String listFairs(Model model) {
        List<Fair> upcomingFairs = fairRepository.findByFairDateGreaterThanEqualAndStatus(LocalDate.now(), FairStatus.UPCOMING);
        List<Fair> activeFairs = fairRepository.findByStatus(FairStatus.ACTIVE);
        List<Fair> finishedFairs = fairRepository.findByStatus(FairStatus.FINISHED);

        model.addAttribute("title", "Ferias Locales");
        model.addAttribute("currentPage", "fairs");
        model.addAttribute("upcomingFairs", upcomingFairs);
        model.addAttribute("activeFairs", activeFairs);
        model.addAttribute("finishedFairs", finishedFairs);
        return "fairs";
    }

    @GetMapping("/{id}")
    public String fairDetail(@PathVariable Long id, Model model) {
        Fair fair = fairRepository.findById(id).orElse(null);
        model.addAttribute("title", "Detalle de Feria");
        model.addAttribute("currentPage", "fairs");
        model.addAttribute("fair", fair);
        return "fair-detail";
    }
}