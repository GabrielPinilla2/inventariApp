package com.erp.inventariapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.inventariapp.entities.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    Page<Measurement> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    Optional<List<Measurement>> findByNameContainingIgnoreCase(String name);
}
