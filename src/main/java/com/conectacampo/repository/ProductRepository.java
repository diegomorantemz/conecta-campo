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

    List<Product> findByCategory(String category);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByVarietyContainingIgnoreCase(String variety);

    List<Product> findByMinPriceLessThanEqual(Double maxPrice);

    @Query("SELECT p FROM Product p WHERE p.seasonStart <= :currentMonth AND p.seasonEnd >= :currentMonth")
    List<Product> findProductsInSeason(@Param("currentMonth") String currentMonth);

    Optional<Product> findByNameAndVariety(String name, String variety);

    List<Product> findByCategoryAndMinPriceLessThanEqual(String category, Double maxPrice);
}