package com.erp.inventariapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.inventariapp.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
        SELECT c
        FROM Customer c
        WHERE c.person.name LIKE CONCAT('%', :name, '%')
    """)
    Optional<List<Customer>> findByNameContainingIgnoreCase(String name);
    

    //Optional<List<Customer>> findByName(String name);
}

