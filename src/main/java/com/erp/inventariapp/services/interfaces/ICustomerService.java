package com.erp.inventariapp.services.interfaces;

import java.util.List;

import com.erp.inventariapp.dto.CustomerDTO;

public interface ICustomerService {
    public List<CustomerDTO> findAll();
    public CustomerDTO findById(Long idcustomer);
    public List<CustomerDTO> findByName(String name);
    public CustomerDTO create(CustomerDTO dto);
    public CustomerDTO update(Long id, CustomerDTO dto);
    public void delete(Long idcustomer);
}
