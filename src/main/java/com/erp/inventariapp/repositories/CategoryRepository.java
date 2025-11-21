package com.erp.inventariapp.repositories;

import com.erp.inventariapp.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<List<Category>> findByNameContainingIgnoreCase(String name);
}
