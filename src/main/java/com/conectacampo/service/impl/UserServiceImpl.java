package com.conectacampo.service.impl;

import com.conectacampo.dto.request.RegisterRequest;
import com.conectacampo.dto.response.UserResponse;
import com.conectacampo.exception.ResourceNotFoundException;
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
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        if (userRepository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        if (request.getPhone() == null || !request.getPhone().matches("^[0-9]{9}$")) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos numéricos");
        }

        if (request.getEmail() == null || !request.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("El email no es válido (ejemplo: usuario@dominio.com)");
        }

        if (request.getDni() == null || !request.getDni().matches("^[0-9]{8}$")) {
            throw new IllegalArgumentException("El DNI debe tener 8 dígitos numéricos");
        }

        if (request.getName() == null || !request.getName().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ]+(?: [A-Za-zÁÉÍÓÚáéíóúñÑ]+)*$")) {
            throw new IllegalArgumentException("Los nombres solo deben contener letras");
        }

        if (request.getLastname() == null || !request.getLastname().matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ]+(?: [A-Za-zÁÉÍÓÚáéíóúñÑ]+)*$")) {
            throw new IllegalArgumentException("Los apellidos solo deben contener letras");
        }

        User user = new User();
        user.setDni(request.getDni());
        user.setName(request.getName().trim());
        user.setLastname(request.getLastname().trim());
        user.setPhone(request.getPhone().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDepartment(request.getDepartment());
        user.setProvince(request.getProvince());
        user.setDistrict(request.getDistrict());

        if ("farmer".equalsIgnoreCase(request.getUserType())) {
            user.setRole(Role.FARMER);
        } else {
            user.setRole(Role.BUYER);
        }

        user.setEnabled(true);

        User saved = userRepository.save(user);
        return convertToResponse(saved);
    }

    @Override
    public UserResponse updateProfile(String email, String name, String lastname, String phone,
                                      String department, String province, String district) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (phone == null || !phone.matches("^[0-9]{9}$")) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos numéricos");
        }

        if (name == null || !name.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{2,50}$")) {
            throw new IllegalArgumentException("Los nombres solo deben contener letras (mínimo 2 caracteres)");
        }

        if (lastname == null || !lastname.matches("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{2,50}$")) {
            throw new IllegalArgumentException("Los apellidos solo deben contener letras (mínimo 2 caracteres)");
        }

        user.setName(name.trim());
        user.setLastname(lastname.trim());
        user.setPhone(phone.trim());
        user.setDepartment(department);
        user.setProvince(province);
        user.setDistrict(district);

        return convertToResponse(userRepository.save(user));
    }

    @Override
    public void changePassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Contraseña actual incorrecta");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Las nuevas contraseñas no coinciden");
        }

        if (newPassword.length() < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("La nueva contraseña debe ser diferente a la actual");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));
        return convertToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado");
        }
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