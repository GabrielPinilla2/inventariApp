package com.erp.inventariapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.inventariapp.dto.CategoryDTO;
import com.erp.inventariapp.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Tag(name="Categories")
public class CategoryController {
    @Autowired
    CategoryService categoryservice;

    @GetMapping
    @Operation(summary = "Obtener listado de todas las Categorías")
    public ResponseEntity<List<CategoryDTO>> listAll(@RequestParam(required = false) String name){
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(categoryservice.findByName(name));
        }        
        return ResponseEntity.ok(categoryservice.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una Categoría por Id")
    public ResponseEntity<CategoryDTO> listById(@PathVariable Long id){
        return ResponseEntity.ok(categoryservice.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva Categoría")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryservice.create(categoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Categoría")
    public CategoryDTO update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        return categoryservice.update(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una Categoría por Id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryservice.delete(id);
        return ResponseEntity.noContent().build();
    }

}
