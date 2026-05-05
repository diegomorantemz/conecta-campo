package com.conectacampo.repository;

import com.conectacampo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar por categoría
    List<Product> findByCategory(String category);

    // Buscar por nombre (contiene, ignorando mayúsculas/minúsculas)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Buscar por variedad
    List<Product> findByVarietyContainingIgnoreCase(String variety);

    // Buscar productos con precio mínimo menor a cierto valor
    List<Product> findByMinPriceLessThanEqual(Double maxPrice);

    // Buscar productos en temporada
    @Query("SELECT p FROM Product p WHERE p.seasonStart <= :currentMonth AND p.seasonEnd >= :currentMonth")
    List<Product> findProductsInSeason(@Param("currentMonth") String currentMonth);

    // Buscar por nombre y variedad (para DataInitializer)
    Optional<Product> findByNameAndVariety(String name, String variety);

    // Buscar por categoría y rango de precio
    List<Product> findByCategoryAndMinPriceLessThanEqual(String category, Double maxPrice);
}