package com.erp.inventariapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SaleProductDTO {

    @NotNull
    private Long productId;

    @Positive
    private Integer quantity;
}