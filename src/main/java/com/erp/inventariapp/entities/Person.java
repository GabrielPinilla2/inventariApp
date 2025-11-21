package com.erp.inventariapp.entities;

import java.time.LocalDate;
import java.util.List;

import com.erp.inventariapp.enums.GenreEnum;
import com.erp.inventariapp.enums.TypeIdEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Builder
@Entity
@Table(name="Person", uniqueConstraints = {
                @UniqueConstraint(columnNames = {"typeId", "identification"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idperson;

    /**
     * Tipo Id de la persona, ENUM(CC, NI, CE, PS, TI, RC).
     * Se crea un ENUM en ./ENUMS/ETypeId
     */
    @Column(name = "typeId", length=2, nullable=false)
    @Enumerated(EnumType.STRING)
    private TypeIdEnum typeId; 
    
    /**
     * Identificación de una persona
     * 
     */
    @Column(length=13, nullable=false)
    private String identification;
    
    @Column(length=100, nullable=false)
    private String name;

    @Column(length=200, nullable=false)
    private String adress;

    @Column(length=200, nullable=false)
    private String email;
    
    @Column(length=10, nullable=false)
    private String phone;

    @Column(nullable=false)
    private LocalDate birthdate;

    /**
     * Genero es ENUM (M, F, O)
     * 
     */
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //Evitar ciclos infinitos
    private Customer customer;
    
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //Evitar ciclos infinitos
    private Seller seller;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore //Evitar ciclos infinitos
    private List<UserApp> users;      
    
}