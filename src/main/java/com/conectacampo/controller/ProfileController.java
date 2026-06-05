package com.conectacampo.controller;

import com.conectacampo.model.User;
import com.conectacampo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        model.addAttribute("user", user);
        model.addAttribute("title", "Mi Perfil");
        model.addAttribute("currentPage", "profile");
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @RequestParam String name,
            @RequestParam String lastname,
            @RequestParam String phone,
            @RequestParam String department,
            @RequestParam String province,
            @RequestParam String district,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/profile";
        }

        // VALIDACIÓN DEL TELÉFONO
        if (phone == null || phone.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El teléfono es obligatorio.");
            return "redirect:/profile";
        }

        if (!phone.matches("^[0-9]{9}$")) {
            redirectAttributes.addFlashAttribute("error", "El teléfono debe tener exactamente 9 dígitos numéricos.");
            return "redirect:/profile";
        }

        // Actualizar datos
        user.setName(name);
        user.setLastname(lastname);
        user.setPhone(phone);
        user.setDepartment(department);
        user.setProvince(province);
        user.setDistrict(district);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente.");
        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/profile";
        }

        // Verificar contraseña actual
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Contraseña actual incorrecta.");
            return "redirect:/profile";
        }

        // Verificar que las nuevas contraseñas coincidan
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las nuevas contraseñas no coinciden.");
            return "redirect:/profile";
        }

        // Verificar que la nueva contraseña tenga al menos 6 caracteres
        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("error", "La nueva contraseña debe tener al menos 6 caracteres.");
            return "redirect:/profile";
        }

        // Actualizar contraseña
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Contraseña actualizada correctamente.");
        return "redirect:/profile";
    }
}