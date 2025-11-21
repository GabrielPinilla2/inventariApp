package com.erp.inventariapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erp.inventariapp.entities.UserApp;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByUsername(String username);

    @Query("""
        SELECT u
        FROM UserApp u
        WHERE u.person.name LIKE CONCAT('%', :name, '%')
    """)
    Optional<List<UserApp>> findByNameContainingIgnoreCase(String name);    
}
