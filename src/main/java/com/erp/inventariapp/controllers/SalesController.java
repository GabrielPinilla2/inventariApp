package com.erp.inventariapp.controllers;

import com.erp.inventariapp.dto.SaleDTO;
import com.erp.inventariapp.services.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name="Sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    @Operation(summary = "Obtener listado de todas las ventas")
    public ResponseEntity<List<SaleDTO>> listAll(){
        return ResponseEntity.ok(salesService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una venta por ID")
    public ResponseEntity<SaleDTO> listById(@PathVariable Long id){
        return ResponseEntity.ok(salesService.findById(id));
    }

    @GetMapping("/findByCustomerId/{idCustomer}")
    @Operation(summary = "Obtener listado de ventas por ID de cliente")
    public ResponseEntity<List<SaleDTO>> findByCategoryId(@PathVariable Long idCustomer){
        return ResponseEntity.ok(salesService.findByCustomerId(idCustomer));
    }

    @GetMapping("/findBySellerId/{idSeller}")
    @Operation(summary = "Obtener listado de ventas por ID de vendedor")
    public ResponseEntity<List<SaleDTO>> findBySellerId(@PathVariable Long idSeller){
        return ResponseEntity.ok(salesService.findBySellerId(idSeller));
    }

    @GetMapping("/findByProductId/{idProduct}")
    @Operation(summary = "Obtener listado de ventas por ID de producto")
    public ResponseEntity<List<SaleDTO>> findByProductId(@PathVariable Long idProduct){
        return ResponseEntity.ok(salesService.findByProductId(idProduct));
    }

    @PostMapping
    @Operation(summary = "Crear nueva venta")
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody SaleDTO saleDTO) {
        return new ResponseEntity<>(salesService.create(saleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una venta")
    public SaleDTO update(@PathVariable Long id, @Valid @RequestBody SaleDTO saleDTO) {
        return salesService.update(id, saleDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una venta por ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
