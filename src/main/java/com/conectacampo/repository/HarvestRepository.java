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

    // Buscar cosechas por finca
    List<Harvest> findByFarm(Farm farm);

    // Buscar cosechas por estado
    List<Harvest> findByStatus(HarvestStatus status);

    // Buscar cosechas activas (alias del método anterior, usa findByStatus)
    // Para obtener cosechas activas: repository.findByStatus(HarvestStatus.ACTIVE)

    // Buscar cosechas por producto
    List<Harvest> findByProductId(Long productId);

    // Buscar cosechas por rango de fechas
    List<Harvest> findByHarvestDateBetween(LocalDate start, LocalDate end);

    // Buscar cosechas por finca y estado
    List<Harvest> findByFarmAndStatus(Farm farm, HarvestStatus status);

    // Buscar cosechas por finca y producto
    List<Harvest> findByFarmAndProductId(Farm farm, Long productId);

    // Buscar cosechas activas de una finca específica
    default List<Harvest> findActiveHarvestsByFarm(Farm farm) {
        return findByFarmAndStatus(farm, HarvestStatus.ACTIVE);
    }
}