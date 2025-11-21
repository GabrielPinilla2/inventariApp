package com.erp.inventariapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SaleProduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleProduct {

    @EmbeddedId
    private SaleProductId id = new SaleProductId();

    @ManyToOne
    @MapsId("idSale")
    private Sale sale;

    @ManyToOne
    @MapsId("idproduct")
    private Product product;

    private int quantity;

}
