package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.CategoryDTO;
import com.erp.inventariapp.entities.Category;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.CategoryRepository;
import com.erp.inventariapp.services.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    CategoryRepository categoryrepository;
    
    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = (List<Category>) categoryrepository.findAll();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        categories.forEach(c -> categoriesDTO.add(this.convertToDTO(c)));
        return categoriesDTO;
    }

    @Override
    public CategoryDTO findById(Long idcategory) {
        Category c = categoryrepository.findById(idcategory).get();
        return (this.convertToDTO(c)); 
    }

    @Override
    public List<CategoryDTO> findByName(String name) {
        Optional<List<Category>> optionalcategories = categoryrepository.findByNameContainingIgnoreCase(name);
        if(optionalcategories.isPresent()){
            List<Category> categories = optionalcategories.get();
            List<CategoryDTO> categoriesDTO = new ArrayList<>();
            categories.forEach(c -> categoriesDTO.add(this.convertToDTO(c)));
            return categoriesDTO;
        }else{
            throw new ResourceNotFoundException("nameCateg");
        }
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        Category c = new Category();
        return saveOrUpdate(c, dto);
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category c = categoryrepository.findById(id).get();
        return saveOrUpdate(c, dto);
    }

    @Override
    public void delete(Long idcategory) {
        //if (!categoryrepository.existsById(idcategory))
        //    throw ResourceNotFoundException("Categoría no encontrada");
        categoryrepository.deleteById(idcategory);
    }

    private CategoryDTO saveOrUpdate(Category c, CategoryDTO dto) {
        c.setName(dto.getName());
        c.setState(dto.getState());
        return convertToDTO(categoryrepository.save(c));
    }

    private CategoryDTO convertToDTO(Category c) {
        CategoryDTO dto = new CategoryDTO();
        dto.setIdcategory(c.getIdcategory());
        dto.setName(c.getName());
        dto.setState(c.getState());
        return dto;
    }    
}
