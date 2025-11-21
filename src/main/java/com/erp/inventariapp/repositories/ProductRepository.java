package com.erp.inventariapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.inventariapp.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    //List<Product> findAll(Sort sort);

    @Query("""
        SELECT p
        FROM Product p JOIN Category c ON p.category.idcategory = c.idcategory 
        JOIN Measurement m ON p.measurement.idmeasurement = m.idmeasurement
        WHERE p.code Like CONCAT('%', :code, '%')
        ORDER BY p.name
        """)    
    List<Product> findByCodeLike(@Param("code") String code);

    @Query("""
        SELECT p
        FROM Product p JOIN Category c ON p.category.idcategory = c.idcategory 
        JOIN Measurement m ON p.measurement.idmeasurement = m.idmeasurement
        WHERE p.name Like CONCAT('%', :name, '%')
        ORDER BY p.name
        """)     
    List<Product> findByNameLike(@Param("name") String name);

    @Query("""
        SELECT p
        FROM Product p JOIN Category c ON p.category.idcategory = c.idcategory 
        JOIN Measurement m ON p.measurement.idmeasurement = m.idmeasurement
        WHERE c.idcategory = :idcategory
        ORDER BY p.name
        """)     
    List<Product> findByCategoryId(@Param("idcategory") Long idcategory);

    @Query("""
        SELECT p
        FROM Product p JOIN Category c ON p.category.idcategory = c.idcategory 
        JOIN Measurement m ON p.measurement.idmeasurement = m.idmeasurement
        WHERE c.name Like CONCAT('%', :categoryname, '%')
        ORDER BY p.name
        """)    
    List<Product> findByCategoryName(@Param("categoryname") String categoryname);    

}
