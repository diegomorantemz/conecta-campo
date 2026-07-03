package com.conectacampo.repository;

import com.conectacampo.model.Harvest;
import com.conectacampo.model.Farm;
import com.conectacampo.model.enums.HarvestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {

    List<Harvest> findByFarm(Farm farm);

    List<Harvest> findByStatus(HarvestStatus status);

    List<Harvest> findByProductId(Long productId);

    List<Harvest> findByHarvestDateBetween(LocalDate start, LocalDate end);

    List<Harvest> findByFarmAndStatus(Farm farm, HarvestStatus status);

    List<Harvest> findByFarmAndProductId(Farm farm, Long productId);

    List<Harvest> findByFarmUserId(Long userId);

    default List<Harvest> findActiveHarvestsByFarm(Farm farm) {
        return findByFarmAndStatus(farm, HarvestStatus.ACTIVE);
    }
}