package com.conectacampo.controller;

import com.conectacampo.model.Product;
import com.conectacampo.model.User;
import com.conectacampo.repository.ProductRepository;
import com.conectacampo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Dashboard del administrador
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        User admin = userRepository.findByEmail(email).orElse(null);

        long totalProducts = productRepository.count();
        long totalFarmers = userRepository.countByRole(com.conectacampo.model.enums.Role.FARMER);
        long totalBuyers = userRepository.countByRole(com.conectacampo.model.enums.Role.BUYER);

        model.addAttribute("adminName", admin != null ? admin.getName() : "Admin");
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalFarmers", totalFarmers);
        model.addAttribute("totalBuyers", totalBuyers);
        model.addAttribute("title", "Panel de Administración");
        model.addAttribute("currentPage", "admin-dashboard");
        return "admin/dashboard";
    }

    // CRUD de Productos
    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("title", "Gestionar Productos");
        model.addAttribute("currentPage", "admin-products");
        return "admin/products";
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("title", "Nuevo Producto");
        model.addAttribute("currentPage", "admin-products");
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        try {
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("success", "Producto guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el producto.");
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Producto no encontrado.");
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("title", "Editar Producto");
        model.addAttribute("currentPage", "admin-products");
        return "admin/product-form";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar el producto porque tiene cosechas asociadas.");
        }
        return "redirect:/admin/products";
    }
}