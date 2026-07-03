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

    List<FairAttendee> findByFair(Fair fair);

    List<FairAttendee> findByBuyer(User buyer);

    Optional<FairAttendee> findByFairAndBuyer(Fair fair, User buyer);

    long countByFair(Fair fair);
}