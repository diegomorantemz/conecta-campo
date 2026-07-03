package com.conectacampo.controller;

import com.conectacampo.service.UserService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

    private final UserService userService;

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        model.addAttribute("user", userService.getAuthenticatedUser(authentication));
        model.addAttribute("title", "Mi Perfil");
        model.addAttribute("currentPage", "profile");
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @RequestParam @Pattern(
                    regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ]+(?: [A-Za-zÁÉÍÓÚáéíóúñÑ]+)*$",
                    message = "Los nombres solo deben contener letras (ejemplo: Juan Carlos)"
            ) String name,

            @RequestParam @Pattern(
                    regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ]+(?: [A-Za-zÁÉÍÓÚáéíóúñÑ]+)*$",
                    message = "Los apellidos solo deben contener letras (ejemplo: Pérez García)"
            ) String lastname,

            @RequestParam @Pattern(
                    regexp = "^[0-9]{9}$",
                    message = "El teléfono debe tener 9 dígitos numéricos"
            ) String phone,

            @RequestParam String department,
            @RequestParam String province,
            @RequestParam String district,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            String email = authentication.getName();
            userService.updateProfile(email, name, lastname, phone, department, province, district);
            redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el perfil.");
        }
        return "redirect:/profile";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            String email = authentication.getName();
            userService.changePassword(email, currentPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("success", "Contraseña actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar la contraseña.");
        }
        return "redirect:/profile";
    }
}