package com.erp.inventariapp.services.interfaces;

import java.util.List;

import com.erp.inventariapp.dto.PersonDTO;

public interface IPersonService {
    public List<PersonDTO> findAll();
    public PersonDTO findById(Long idperson);
    public List<PersonDTO> findByName(String name);
    public PersonDTO create(PersonDTO dto);
    public PersonDTO update(Long id, PersonDTO dto);
    public void delete(Long idperson);
}
