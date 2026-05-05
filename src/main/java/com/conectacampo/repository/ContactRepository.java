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

    // Buscar contactos por cosecha
    List<Contact> findByHarvest(Harvest harvest);

    // Buscar contactos por comprador
    List<Contact> findByBuyer(User buyer);

    // Buscar contactos por estado
    List<Contact> findByStatus(ContactStatus status);

    // Buscar contactos por cosecha y estado
    List<Contact> findByHarvestAndStatus(Harvest harvest, ContactStatus status);
}