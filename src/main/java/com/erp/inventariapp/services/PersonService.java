package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.PersonDTO;
import com.erp.inventariapp.entities.Person;
import com.erp.inventariapp.exceptions.ResourceDeleteException;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.PersonRepository;
import com.erp.inventariapp.services.interfaces.IPersonService;

@Service
public class PersonService implements IPersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<PersonDTO> findAll() {
        List<Person> persons = (List<Person>)personRepository.findAll();
        List<PersonDTO> dtos = new ArrayList<>();
        persons.forEach(p -> dtos.add(convertToDTO(p)));
        return dtos;
    }

    @Override
    public PersonDTO findById(Long idperson) {
        Person person = personRepository.findById(idperson).get();
        return convertToDTO(person);
    }

    @Override
    public List<PersonDTO> findByName(String name) {
        Optional<List<Person>> optionalpersons = personRepository.findByNameContainingIgnoreCase(name);
        if(optionalpersons.isPresent()){
            List<Person> persons = optionalpersons.get();
            List<PersonDTO> personsDTO = new ArrayList<>();
            persons.forEach(c -> personsDTO.add(this.convertToDTO(c)));
            return personsDTO;
        }else{
            throw new ResourceNotFoundException("namePersons");
        }
    }    

    @Override
    public PersonDTO create(PersonDTO dto) {
        Person person = new Person();
        person = convertToPerson(person, dto);
        return convertToDTO(personRepository.save(person));
    }

    @Override
    public PersonDTO update(Long id, PersonDTO dto) {
        Person person = personRepository.findById(id).get();
        person = convertToPerson(person, dto);
        return convertToDTO(personRepository.save(person));
    }

    @Override
    public void delete(Long idperson) {
        try {
            if (personRepository.existsById(idperson)) {
                personRepository.deleteById(idperson);
            } else {
                throw new ResourceNotFoundException("La persona con ID " + idperson + " NO existe.");
            }
        } catch (Exception e) {
            throw new ResourceDeleteException("Error al eliminar la persona.", e);
        }      
    }

    private PersonDTO convertToDTO(Person p){
        PersonDTO dto = new PersonDTO();
        dto.setTypeId(p.getTypeId());
        dto.setIdentification(p.getIdentification());
        dto.setIdperson(p.getIdperson());
        dto.setTypeId(p.getTypeId());
        dto.setIdentification(p.getIdentification());
        dto.setName(p.getName());
        dto.setAdress(p.getAdress());
        dto.setBirthdate(p.getBirthdate());
        dto.setPhone(p.getPhone());
        dto.setEmail(p.getEmail());
        dto.setGenre(p.getGenre());
        
        return dto;
    }

    private Person convertToPerson(Person p, PersonDTO dto){
        p.setIdperson(dto.getIdperson());
        p.setTypeId(dto.getTypeId());
        p.setIdentification(dto.getIdentification());
        p.setName(dto.getName());
        p.setAdress(dto.getAdress());
        p.setBirthdate(dto.getBirthdate());
        p.setPhone(dto.getPhone());
        p.setEmail(dto.getEmail());
        p.setGenre(dto.getGenre());

        return p;
    }

}
