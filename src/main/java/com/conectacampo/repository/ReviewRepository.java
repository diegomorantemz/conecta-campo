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

    List<Review> findByHarvest(Harvest harvest);

    List<Review> findByBuyer(User buyer);

    List<Review> findByRatingGreaterThanEqual(Integer rating);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.harvest.farm.user.id = :farmerId")
    Double getAverageRatingByFarmerId(@Param("farmerId") Long farmerId);
}