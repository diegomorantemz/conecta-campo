package com.conectacampo.repository;

import com.conectacampo.model.User;
import com.conectacampo.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    List<User> findByRole(Role role);
    List<User> findByDistrict(String district);
    List<User> findByEnabledTrue();
    long countByRole(Role role);
}