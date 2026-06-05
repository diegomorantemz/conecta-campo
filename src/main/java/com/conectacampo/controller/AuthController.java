package com.conectacampo.controller;

import com.conectacampo.dto.request.RegisterRequest;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.Role;
import com.conectacampo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterRequest request,
                               RedirectAttributes redirectAttributes) {

        // Validar que las contraseñas coincidan
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/register";
        }

        // Validar DNI (8 dígitos)
        if (request.getDni() == null || request.getDni().length() != 8) {
            redirectAttributes.addFlashAttribute("error", "El DNI debe tener 8 dígitos");
            return "redirect:/register";
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "El correo ya está registrado");
            return "redirect:/register";
        }

        // Verificar si el DNI ya existe
        if (userRepository.existsByDni(request.getDni())) {
            redirectAttributes.addFlashAttribute("error", "El DNI ya está registrado");
            return "redirect:/register";
        }

        // Validar teléfono (solo números, 9 dígitos para celular)
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            if (!request.getPhone().matches("^[0-9]{9}$")) {
                redirectAttributes.addFlashAttribute("error", "El teléfono debe tener 9 dígitos numéricos");
                return "redirect:/register";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "El teléfono es obligatorio");
            return "redirect:/register";
        }

        // Crear nuevo usuario
        User user = new User();
        user.setDni(request.getDni());
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());

        // ENCRIPTAR CONTRASEÑA
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        //Guardar ubicación (3 niveles)
        user.setDepartment(request.getDepartment());
        user.setProvince(request.getProvince());
        user.setDistrict(request.getDistrict());

        // Asignar rol
        if ("farmer".equals(request.getUserType())) {
            user.setRole(Role.FARMER);
        } else {
            user.setRole(Role.BUYER);
        }

        user.setEnabled(true);

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión.");
        return "redirect:/login";
    }
}