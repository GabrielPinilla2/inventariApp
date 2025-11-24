package com.erp.inventariapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Sale")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSale;

    @Column(nullable=false)
    private LocalDate date;

    @Column(nullable=false)
    private Double totalAmount;

    private Double discount;

    private Double tax;

    @Column(nullable=false)
    private Boolean state;

    @Column(length=250)
    private String observation;

    @ManyToOne
    @JoinColumn(name = "idcustomer", referencedColumnName = "idcustomer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idseller", referencedColumnName = "idseller")
    private Seller seller;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleProduct> saleProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    private UserApp user;
}
