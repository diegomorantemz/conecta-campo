package com.conectacampo.repository;

import com.conectacampo.model.FairProduct;
import com.conectacampo.model.Fair;
import com.conectacampo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FairProductRepository extends JpaRepository<FairProduct, Long> {

    // Buscar productos por feria
    List<FairProduct> findByFair(Fair fair);

    // Buscar ferias por producto
    List<FairProduct> findByProduct(Product product);
}