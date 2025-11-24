package com.erp.inventariapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class SaleDTO {

    private Long idSale;

    @Positive
    private Double totalAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @PositiveOrZero
    private Double discount;

    @PositiveOrZero
    private Double tax;

    @NotNull
    private Boolean state;

    private String observation;

    @NotNull
    private Long customerId;

    @NotNull
    private Long sellerId;

    @NotNull
    private List<SaleProductDTO> products;
}
