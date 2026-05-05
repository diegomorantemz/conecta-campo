package com.conectacampo.repository;

import com.conectacampo.model.Fair;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.FairStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FairRepository extends JpaRepository<Fair, Long> {

    // Buscar ferias por agricultor
    List<Fair> findByFarmer(User farmer);

    // Buscar ferias por estado
    List<Fair> findByStatus(FairStatus status);

    // Buscar ferias por fecha
    List<Fair> findByFairDate(LocalDate date);

    // Buscar ferias próximas (fecha mayor o igual a hoy)
    List<Fair> findByFairDateGreaterThanEqualAndStatus(LocalDate date, FairStatus status);

    // Buscar ferias por distrito (por ubicación)
    List<Fair> findByLocationNameContainingIgnoreCase(String location);

    // Buscar ferias cercanas por coordenadas (consulta nativa)
    @Query(value = "SELECT * FROM fairs WHERE location_lat BETWEEN :latMin AND :latMax AND location_lng BETWEEN :lngMin AND :lngMax", nativeQuery = true)
    List<Fair> findNearbyFairs(@Param("latMin") Double latMin, @Param("latMax") Double latMax,
                               @Param("lngMin") Double lngMin, @Param("lngMax") Double lngMax);
}