package com.erp.inventariapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long idproduct;

    @NotBlank 
    private String code;

    @NotBlank
    private String name;

    @NotNull @Positive
    private Double price;

    @NotNull @Positive
    private Double stock;
    
    @NotNull @Positive
    private Double stockmin;
    
    @NotNull @Positive
    private Double stockmax;

    @NotNull
    private Boolean state;
    
    @NotNull
    private Long idcategory;
    
    private String namecategory;
    
    @NotNull
    private Long idmeasurement;

    private String namemeasurement;
}
