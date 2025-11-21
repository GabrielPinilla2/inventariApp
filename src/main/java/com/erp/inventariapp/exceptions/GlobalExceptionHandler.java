package com.erp.inventariapp.exceptions;

import org.apache.coyote.BadRequestException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Manejador de la Exception NOT FOUND.
     *
     * @param ex ResourceNotFoundException
     * @return Mensaje de Recurso NO encontrado.
     */    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Manejador de la Exception BAD REQUEST.
     *
     * @param ex BadRequestException
     * @return Mensaje de petición incorrecta.
     */    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador de Exception en General.
     *
     * @param ex Exception
     * @return Mensaje de Error interno.
     */
    
    //*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        // Ignora excepciones de swagger
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            System.out.println("***** Entra a : "+path);
            throw new RuntimeException(ex); // deja que el framework lo maneje
        }        
        System.out.println("***** Error: "+ex.hashCode());
        System.out.println("***** Error: "+ex.getMessage()+" ***");
        return new ResponseEntity<>("Error interno del servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //*/
}