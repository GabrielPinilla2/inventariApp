package com.erp.inventariapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.inventariapp.dto.SellerDTO;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.services.SellerService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/sellers")
@Tag(name="Sellers")
public class SellerController {
    
    @Autowired
    SellerService sellerService;
    
    @GetMapping
    public ResponseEntity<List<SellerDTO>> findAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(sellerService.findByName(name));
        }                
        return new ResponseEntity<>(sellerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> findById(@PathVariable Long id) {
        SellerDTO dto = sellerService.findById(id);
        if (dto.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        return new ResponseEntity<>(dto, HttpStatus.OK); // 200 OK        
    }

    @PostMapping
    public ResponseEntity<SellerDTO> create(@RequestBody SellerDTO dto) {
        SellerDTO ret = sellerService.create(dto);
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerDTO> update(@PathVariable Long id, @RequestBody SellerDTO dto) {
        SellerDTO ret = sellerService.update(id, dto);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestParam Long id){
        try {
            sellerService.delete(id);
            return new ResponseEntity<>("Seller eliminado correctamente", HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("No se encuentra el Seller con ID: "+id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al intentar eliminar el Seller con ID: "+id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
