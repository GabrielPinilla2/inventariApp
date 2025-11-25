package com.erp.inventariapp.repositories;
import com.erp.inventariapp.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT s
        FROM Sale s
        WHERE s.customer.idcustomer = :customerId
        ORDER BY s.date DESC
        """)
    List<Sale> findByCustomerId(@Param("customerId") Long customerId);

    @Query("""
        SELECT s
        FROM Sale s
        WHERE s.seller.idseller = :sellerId
        ORDER BY s.date DESC
        """)
    List<Sale> findBySellerId(@Param("sellerId") Long sellerId);

    @Query("""
        SELECT s
        FROM Sale s
        JOIN s.saleProducts sp
        WHERE sp.product.id = :productId
        """)
    List<Sale> findByProductId(@Param("productId") Long productId);

    /*@Query("""
        SELECT new com.erp.inventariapp.dto.SalesChartDTO(
            FUNCTION('MONTHNAME', s.date),
            SUM(s.totalAmount)
        )
        FROM Sale s
        GROUP BY FUNCTION('MONTH', s.date)
        """)
    List<com.erp.inventariapp.dto.SalesChartDTO> getMonthlySales();*/

}
