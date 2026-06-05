package com.conectacampo.service;

import com.conectacampo.dto.request.RegisterRequest;
import com.conectacampo.dto.response.UserResponse;
import com.conectacampo.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    UserResponse updateProfile(String email, String name, String lastname, String phone,
                               String department, String province, String district);
    void changePassword(String email, String currentPassword, String newPassword, String confirmPassword);
    User getAuthenticatedUser(Authentication authentication);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
}