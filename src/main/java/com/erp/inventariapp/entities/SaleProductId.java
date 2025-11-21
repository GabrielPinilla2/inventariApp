package com.erp.inventariapp.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class SaleProductId implements Serializable {

    private Long idSale;
    private Long idproduct;

}
