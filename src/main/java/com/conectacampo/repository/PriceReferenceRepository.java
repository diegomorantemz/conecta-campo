package com.conectacampo.repository;

import com.conectacampo.model.PriceReference;
import com.conectacampo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceReferenceRepository extends JpaRepository<PriceReference, Long> {

    // Buscar por producto
    List<PriceReference> findByProduct(Product product);

    // Buscar por producto y distrito
    Optional<PriceReference> findByProductAndDistrict(Product product, String district);

    // Buscar precios de referencia por fecha
    List<PriceReference> findByReferenceDate(LocalDate date);

    // Buscar últimos precios de referencia por producto
    List<PriceReference> findTopByProductOrderByReferenceDateDesc(Product product);
}