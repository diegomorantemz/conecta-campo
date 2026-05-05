package com.conectacampo.repository;

import com.conectacampo.model.Review;
import com.conectacampo.model.Harvest;
import com.conectacampo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Buscar reseñas por cosecha
    List<Review> findByHarvest(Harvest harvest);

    // Buscar reseñas por comprador
    List<Review> findByBuyer(User buyer);

    // Buscar reseñas con calificación mayor a X
    List<Review> findByRatingGreaterThanEqual(Integer rating);

    // Calcular promedio de calificación de un agricultor (por sus cosechas)
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.harvest.farm.user.id = :farmerId")
    Double getAverageRatingByFarmerId(@Param("farmerId") Long farmerId);
}