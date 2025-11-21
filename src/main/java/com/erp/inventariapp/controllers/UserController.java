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

import com.erp.inventariapp.dto.UserDTO;
import com.erp.inventariapp.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Tag(name="User")
public class UserController {
    @Autowired
    UserService userservice;

    @GetMapping
    @Operation(summary = "Obtener listado de todos las Usuarios")
    public ResponseEntity<List<UserDTO>> listAll(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(userservice.findByName(name));
        }                
        return new ResponseEntity<>(userservice.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una Usuario por Id")
    public ResponseEntity<UserDTO> listById(Long id){
        return ResponseEntity.ok(userservice.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo Usuario")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userservice.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Usuario")
    public UserDTO update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return (userservice.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una Usuario por Id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userservice.delete(id);
        return ResponseEntity.noContent().build();
    }

}
