package com.erp.inventariapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.inventariapp.dto.CustomerDTO;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.services.CustomerService;

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
@RequestMapping("/api/customer")
@Tag(name="Customers")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;
    
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(customerService.findByName(name));
        }                
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        CustomerDTO dto = customerService.findById(id);
        if (dto.equals(null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        return new ResponseEntity<>(dto, HttpStatus.OK); // 200 OK       
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO dto) {
        CustomerDTO ret = customerService.create(dto);
        return new ResponseEntity<>(ret, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        CustomerDTO ret = customerService.update(id, dto);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestParam Long id){
        try {
            customerService.delete(id);
            return new ResponseEntity<>("Customer eliminado correctamente", HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("No se encuentra el Customer con ID: "+id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al intentar eliminar el Customer con ID: "+id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

