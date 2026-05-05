package com.conectacampo.repository;

import com.conectacampo.model.FairAttendee;
import com.conectacampo.model.Fair;
import com.conectacampo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FairAttendeeRepository extends JpaRepository<FairAttendee, Long> {

    // Buscar asistentes por feria
    List<FairAttendee> findByFair(Fair fair);

    // Buscar ferias a las que asiste un comprador
    List<FairAttendee> findByBuyer(User buyer);

    // Verificar si un comprador ya confirmó asistencia
    Optional<FairAttendee> findByFairAndBuyer(Fair fair, User buyer);

    // Contar asistentes por feria
    long countByFair(Fair fair);
}