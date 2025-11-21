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
import org.springframework.web.bind.annotation.RestController;

import com.erp.inventariapp.dto.ProductDTO;
import com.erp.inventariapp.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Tag(name="Products")
public class ProductController {
    @Autowired
    ProductService productservice;

    @GetMapping
    @Operation(summary = "Obtener listado de todos los Productos")
    public ResponseEntity<List<ProductDTO>> listAll(){
        return ResponseEntity.ok(productservice.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un Producto por Id")
    public ResponseEntity<ProductDTO> listById(@PathVariable Long id){
        return ResponseEntity.ok(productservice.findById(id));
    }

    @GetMapping("/findByCodeLike/{code}")
    @Operation(summary = "Obtener listado de Productos filtrados por Código")
    public ResponseEntity<List<ProductDTO>> findByCodeLike(@PathVariable String codeproduct){
        return ResponseEntity.ok(productservice.findByCodeLike(codeproduct));
    }

    @GetMapping("/findByNameLike/{name}")
    @Operation(summary = "Obtener listado de Productos filtrados por Nombre")
    public ResponseEntity<List<ProductDTO>> findByNameLike(@PathVariable String nameproduct){
        return ResponseEntity.ok(productservice.findByNameLike(nameproduct));
    }

    @GetMapping("/findByCategoryId/{idcategory}")
    @Operation(summary = "Obtener listado de Productos filtrados por Id de Categoría")
    public ResponseEntity<List<ProductDTO>> findByCategoryId(@PathVariable Long idcategory){
        return ResponseEntity.ok(productservice.findByCategoryId(idcategory));
    }

    @GetMapping("/findByCategoryName/{namecategory}")
    @Operation(summary = "Obtener listado de Productos filtrados por Nombre de Categoría")
    public ResponseEntity<List<ProductDTO>> findByCategoryName(@PathVariable String namecategory){
        return ResponseEntity.ok(productservice.findByCategoryName(namecategory));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo Producto")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productservice.create(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Producto")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return productservice.update(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un Producto por Id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productservice.delete(id);
        return ResponseEntity.noContent().build();
    }
}
