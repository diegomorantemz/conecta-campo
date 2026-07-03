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

    List<Fair> findByFarmer(User farmer);

    List<Fair> findByStatus(FairStatus status);

    List<Fair> findByFairDate(LocalDate date);

    List<Fair> findByFairDateGreaterThanEqualAndStatus(LocalDate date, FairStatus status);

    List<Fair> findByLocationNameContainingIgnoreCase(String location);

    @Query(value = "SELECT * FROM fairs WHERE location_lat BETWEEN :latMin AND :latMax AND location_lng BETWEEN :lngMin AND :lngMax", nativeQuery = true)
    List<Fair> findNearbyFairs(@Param("latMin") Double latMin, @Param("latMax") Double latMax,
                               @Param("lngMin") Double lngMin, @Param("lngMax") Double lngMax);
}