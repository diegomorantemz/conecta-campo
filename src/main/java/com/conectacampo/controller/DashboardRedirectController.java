package com.conectacampo.controller;

import com.conectacampo.model.User;
import com.conectacampo.model.enums.Role;
import com.conectacampo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardRedirectController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard/redirect")
    public String redirectByRole(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "redirect:/";
        }

        if (user.getRole() == Role.FARMER) {
            return "redirect:/dashboard/farmer";
        } else if (user.getRole() == Role.BUYER) {
            return "redirect:/dashboard/buyer";
        } else if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/";
    }
}