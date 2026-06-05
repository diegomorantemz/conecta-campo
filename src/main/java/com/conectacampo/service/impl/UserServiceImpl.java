package com.conectacampo.service.impl;

import com.conectacampo.dto.request.RegisterRequest;
import com.conectacampo.dto.response.UserResponse;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.Role;
import com.conectacampo.repository.UserRepository;
import com.conectacampo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        if (userRepository.existsByDni(request.getDni())) {
            throw new RuntimeException("El DNI ya está registrado");
        }

        User user = new User();
        user.setDni(request.getDni());
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDepartment(request.getDepartment());
        user.setProvince(request.getProvince());
        user.setDistrict(request.getDistrict());
        user.setRole("farmer".equals(request.getUserType()) ? Role.FARMER : Role.BUYER);
        user.setEnabled(true);

        User saved = userRepository.save(user);
        return convertToResponse(saved);
    }

    @Override
    public UserResponse updateProfile(String email, String name, String lastname, String phone,
                                      String department, String province, String district) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(name);
        user.setLastname(lastname);
        user.setPhone(phone);
        user.setDepartment(department);
        user.setProvince(province);
        user.setDistrict(district);

        return convertToResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Las nuevas contraseñas no coinciden");
        }
        if (newPassword.length() < 6) {
            throw new RuntimeException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setDni(user.getDni());
        response.setName(user.getName());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole().name());
        response.setDepartment(user.getDepartment());
        response.setProvince(user.getProvince());
        response.setDistrict(user.getDistrict());
        response.setEnabled(user.getEnabled());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}