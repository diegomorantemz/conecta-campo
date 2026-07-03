package com.conectacampo.repository;

import com.conectacampo.model.Farm;
import com.conectacampo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    List<Farm> findByUser(User user);

    List<Farm> findByDistrict(String district);

    List<Farm> findByNameContainingIgnoreCase(String name);

    Optional<Farm> findByName(String name);
}