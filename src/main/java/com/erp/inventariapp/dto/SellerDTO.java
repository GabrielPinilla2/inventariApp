package com.erp.inventariapp.dto;

import java.time.LocalDate;

import com.erp.inventariapp.enums.GenreEnum;
import com.erp.inventariapp.enums.TypeIdEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {
    private Long idseller;

    private Boolean state;

    //private PersonDTO person;

    private Long idperson;

    private TypeIdEnum typeId; 
    
    private String identification;
   
    private String name;

    private String adress;

    private String email;
    
    private String phone;

    private LocalDate birthdate;

    private GenreEnum genre;    
}
