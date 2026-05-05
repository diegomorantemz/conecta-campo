package com.conectacampo.repository;

import com.conectacampo.model.Farm;
import com.conectacampo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    // Buscar fincas por agricultor
    List<Farm> findByUser(User user);

    // Buscar fincas por distrito
    List<Farm> findByDistrict(String district);

    // Buscar fincas por nombre (contiene)
    List<Farm> findByNameContainingIgnoreCase(String name);

    // Buscar finca por nombre exacto (para DataInitializer)
    Optional<Farm> findByName(String name);  // ← Agrega esta línea
}