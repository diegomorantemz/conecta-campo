package com.conectacampo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Inicio");
        model.addAttribute("currentPage", "home");
        return "index";
    }

    @GetMapping("/map")
    public String map(Model model) {
        model.addAttribute("title", "Mapa de Agricultores");
        model.addAttribute("currentPage", "map");
        return "map";
    }

    @GetMapping("/harvests")
    public String harvests(Model model) {
        model.addAttribute("title", "Cosechas Disponibles");
        model.addAttribute("currentPage", "harvests");
        return "harvests";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Iniciar Sesión");
        model.addAttribute("currentPage", "login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Registro de Usuario");
        model.addAttribute("currentPage", "register");
        return "register";
    }

    @GetMapping("/how-it-works")
    public String howItWorks(Model model) {
        model.addAttribute("title", "Cómo Funciona");
        model.addAttribute("currentPage", "how-it-works");
        return "how-it-works";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contacto");
        model.addAttribute("currentPage", "contact");
        return "contact";
    }

    @GetMapping("/fairs")
    public String fairs(Model model) {
        model.addAttribute("title", "Ferias Locales");
        model.addAttribute("currentPage", "fairs");
        return "fairs";
    }

    @GetMapping("/fair/{id}")
    public String fairDetail(Model model) {
        model.addAttribute("title", "Detalle de Feria");
        model.addAttribute("currentPage", "fair-detail");
        return "fair-detail";
    }
}