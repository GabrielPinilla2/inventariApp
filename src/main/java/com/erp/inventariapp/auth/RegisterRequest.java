package com.erp.inventariapp.auth;

import com.erp.inventariapp.dto.PersonDTO;
import com.erp.inventariapp.enums.RoleUserEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    RoleUserEnum roleUser;
    Boolean state;
    PersonDTO person;
}
