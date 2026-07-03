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

    List<PriceReference> findByProduct(Product product);

    Optional<PriceReference> findByProductAndDistrict(Product product, String district);

    List<PriceReference> findByReferenceDate(LocalDate date);

    List<PriceReference> findTopByProductOrderByReferenceDateDesc(Product product);
}