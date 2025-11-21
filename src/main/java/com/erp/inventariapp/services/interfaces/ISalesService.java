package com.erp.inventariapp.services.interfaces;

import com.erp.inventariapp.dto.SaleDTO;

import java.util.List;

public interface ISalesService {

    List<SaleDTO> findAll();
    SaleDTO findById(Long idSale);
    List<SaleDTO> findByCustomerId(Long idCustomer);
    List<SaleDTO> findBySellerId(Long idSeller);
    List<SaleDTO> findByProductId(Long idProduct);
    SaleDTO create(SaleDTO sale);
    SaleDTO update(Long idSale, SaleDTO sale);
    void delete(Long idSale);
}
