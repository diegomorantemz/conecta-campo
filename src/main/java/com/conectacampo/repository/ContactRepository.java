package com.conectacampo.repository;

import com.conectacampo.model.Contact;
import com.conectacampo.model.Harvest;
import com.conectacampo.model.User;
import com.conectacampo.model.enums.ContactStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByHarvest(Harvest harvest);

    List<Contact> findByBuyer(User buyer);

    List<Contact> findByStatus(ContactStatus status);

    List<Contact> findByHarvestAndStatus(Harvest harvest, ContactStatus status);
}