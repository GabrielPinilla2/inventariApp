package com.erp.inventariapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idproduct;

    @Column(length=10, nullable=false, unique=true)
    private String code;

    @Column(length=50, nullable=false)
    private String name;

    @Column(nullable=false)
    private Double price;

    @Column(nullable=false)
    private Double stock;

    @Column(nullable=false)
    private Double stockmin;

    @Column(nullable=false)
    private Double stockmax;

    @Column(nullable=false)
    private Boolean state;

    @ManyToOne
    @JoinColumn(name = "idcategory", referencedColumnName = "idcategory")
    private Category category;    

    @ManyToOne
    @JoinColumn(name = "idmeasurement", referencedColumnName = "idmeasurement")
    private Measurement measurement;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleProduct> saleProducts = new ArrayList<>();

}
