package com.erp.inventariapp.services.interfaces;

import java.util.List;

import com.erp.inventariapp.dto.CategoryDTO;

public interface ICategoryService {

    public List<CategoryDTO> findAll();
    public CategoryDTO findById(Long idcategory);
    public List<CategoryDTO> findByName(String name);
    public CategoryDTO create(CategoryDTO dto);
    public CategoryDTO update(Long id, CategoryDTO dto);
    public void delete(Long idcategory);
}
