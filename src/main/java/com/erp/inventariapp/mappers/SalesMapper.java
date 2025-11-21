package com.erp.inventariapp.mappers;

import com.erp.inventariapp.dto.SaleDTO;
import com.erp.inventariapp.dto.SaleProductDTO;
import com.erp.inventariapp.entities.Sale;
import com.erp.inventariapp.entities.SaleProduct;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SalesMapper {

    public SaleDTO toDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setIdSale(sale.getIdSale());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setDate(sale.getDate());
        dto.setDiscount(sale.getDiscount());
        dto.setTax(sale.getTax());
        dto.setState(sale.getState());
        dto.setObservation(sale.getObservation());

        Long customerId = Optional.ofNullable(sale.getCustomer().getIdcustomer())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el ID del cliente"));

        dto.setCustomerId(customerId);

        Long sellerId = Optional.ofNullable(sale.getSeller().getIdseller())
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el ID del vendedor"));

        dto.setSellerId(sellerId);
        dto.setProducts(sale.getSaleProducts().stream()
                .map(this::convertToSaleProductDTO)
                .toList());

        return dto;
    }

    public Sale toEntity(SaleDTO dto) {
        Sale sale = new Sale();
        sale.setIdSale(dto.getIdSale());
        sale.setDate(dto.getDate());
        sale.setDiscount(dto.getDiscount());
        sale.setTax(dto.getTax());
        sale.setState(dto.getState());
        sale.setObservation(dto.getObservation());

        return sale;
    }

    private SaleProductDTO convertToSaleProductDTO(SaleProduct saleProduct) {
        SaleProductDTO dto = new SaleProductDTO();
        dto.setProductId(saleProduct.getProduct().getIdproduct());
        dto.setQuantity(saleProduct.getQuantity());
        return dto;
    }
}
