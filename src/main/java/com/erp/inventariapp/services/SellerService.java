package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.SellerDTO;
import com.erp.inventariapp.entities.Person;
import com.erp.inventariapp.entities.Seller;
import com.erp.inventariapp.exceptions.ResourceDeleteException;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.PersonRepository;
import com.erp.inventariapp.repositories.SellerRepository;
import com.erp.inventariapp.services.interfaces.ISellerService;

@Service
public class SellerService implements ISellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    PersonRepository personrepository;

    @Autowired
    PersonService personservice;

    @Override
    public List<SellerDTO> findAll() {
        List<Seller> sellers = (List<Seller>)sellerRepository.findAll();
        List<SellerDTO> sellersDTO = new ArrayList<>();
        sellers.forEach(s -> sellersDTO.add(this.convertToDTO(s)));
        return(sellersDTO);
    }

    @Override
    public SellerDTO findById(Long idseller) {
        Seller s = sellerRepository.findById(idseller).get();
        if(s.equals(null)){
            throw new ResourceNotFoundException("El Id: "+idseller+" de Seller NO Existe!");
        }
        return convertToDTO(s);
    }

    @Override
    public List<SellerDTO> findByName(String name) {
        Optional<List<Seller>> optionalsellers = sellerRepository.findByNameContainingIgnoreCase(name);
        if(optionalsellers.isPresent()){
            List<Seller> sellers = optionalsellers.get();
            List<SellerDTO> sellersDTO = new ArrayList<>();
            sellers.forEach(c -> sellersDTO.add(this.convertToDTO(c)));
            return sellersDTO;
        }else{
            throw new ResourceNotFoundException("nameSeller");
        }
    }

    @Override
    public SellerDTO create(SellerDTO dto) {
        Seller s = new Seller();
        s = convertToSeller(s, dto);
        return convertToDTO(sellerRepository.save(s));
    }

    @Override
    public SellerDTO update(Long id, SellerDTO dto) {
        Seller s = new Seller();

        if(!sellerRepository.existsById(id)){
            throw new ResourceNotFoundException("El Id: "+id+" de Seller NO existe!");
        }

        s = sellerRepository.findById(id).get();
        s = convertToSeller(s, dto);
        
        return convertToDTO(sellerRepository.save(s));
    }

    @Override
    public void delete(Long idseller) {
        try {
            if (sellerRepository.existsById(idseller)) {
                sellerRepository.deleteById(idseller);
            } else {
                throw new ResourceNotFoundException("El Seller con ID " + idseller + " NO existe.");
            }
        } catch (Exception e) {
            throw new ResourceDeleteException("Error al eliminar el Seller.", e);
        }        
    }

    private SellerDTO convertToDTO(Seller s){
        SellerDTO dto = new SellerDTO();
        dto.setIdseller(s.getIdseller());
        dto.setState(s.getState());
        
        dto.setIdperson(s.getPerson().getIdperson());
        dto.setTypeId(s.getPerson().getTypeId());
        dto.setIdentification(s.getPerson().getIdentification());
        dto.setName(s.getPerson().getName());
        dto.setAdress(s.getPerson().getAdress());
        dto.setEmail(s.getPerson().getEmail());
        dto.setPhone(s.getPerson().getPhone());
        dto.setBirthdate(s.getPerson().getBirthdate());
        dto.setGenre(s.getPerson().getGenre());

        return dto;
    }

    private Seller convertToSeller(Seller s, SellerDTO dto){
        s.setIdseller(dto.getIdseller());
        s.setState(dto.getState());
        
        System.out.println("****--IDPERSON = "+dto.getIdperson()+" --****");
        Optional<Person> optionalperson = personrepository.findById(dto.getIdperson());
        if(optionalperson.isPresent()){
            s.setPerson(optionalperson.get());
        }else{
            System.out.println("****-- NOT FOUND PERSON --****");
            // Crear nueva persona con los datos del DTO
            Person person = Person.builder()
            .typeId(dto.getTypeId())
            .identification(dto.getIdentification())
            .name(dto.getName())
            .adress(dto.getAdress())
            .email(dto.getEmail())
            .phone(dto.getPhone())
            .birthdate(dto.getBirthdate())
            .genre(dto.getGenre())
            .build();
            
            s.setPerson(person);            
        }
        System.out.println("****-- END convertToSeller --****");        

        return s;
    }    
}
