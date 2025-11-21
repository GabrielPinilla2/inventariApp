package com.erp.inventariapp.services.interfaces;

import java.util.List;

import com.erp.inventariapp.dto.SellerDTO;

public interface ISellerService {
    public List<SellerDTO> findAll();
    public SellerDTO findById(Long idseller);
    public List<SellerDTO> findByName(String name);
    public SellerDTO create(SellerDTO dto);
    public SellerDTO update(Long id, SellerDTO dto);
    public void delete(Long idseller);
}
