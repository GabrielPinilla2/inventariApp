package com.erp.inventariapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.inventariapp.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<List<Person>> findByNameContainingIgnoreCase(String name);
}
