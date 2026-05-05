package com.conectacampo.repository;

import com.conectacampo.model.User;
import com.conectacampo.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar por email
    Optional<User> findByEmail(String email);

    // Verificar si existe email
    boolean existsByEmail(String email);

    // Buscar por rol (esto es correcto aquí)
    List<User> findByRole(Role role);

    // Buscar por distrito
    List<User> findByDistrict(String district);

    // Buscar usuarios activos
    List<User> findByEnabledTrue();
}