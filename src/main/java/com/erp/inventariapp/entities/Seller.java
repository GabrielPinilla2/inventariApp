package com.erp.inventariapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Seller")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idseller;

    @Column(nullable=false)
    private Boolean state;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idperson", referencedColumnName = "idperson")
    private Person person;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sale> sales;
}
