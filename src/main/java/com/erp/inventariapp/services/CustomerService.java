package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.CustomerDTO;
import com.erp.inventariapp.entities.Person;
import com.erp.inventariapp.entities.Customer;
import com.erp.inventariapp.exceptions.ResourceDeleteException;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.PersonRepository;
import com.erp.inventariapp.repositories.CustomerRepository;
import com.erp.inventariapp.services.interfaces.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PersonRepository personrepository;

    @Autowired
    PersonService personservice;

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = (List<Customer>)customerRepository.findAll();
        List<CustomerDTO> customersDTO = new ArrayList<>();
        customers.forEach(s -> customersDTO.add(this.convertToDTO(s)));
        return(customersDTO);
    }

    @Override
    public CustomerDTO findById(Long idcustomer) {
        Customer s = customerRepository.findById(idcustomer).get();
        if(s.equals(null)){
            throw new ResourceNotFoundException("El Id: "+idcustomer+" de Customer NO Existe!");
        }
        return convertToDTO(s);
    }

    @Override
    public List<CustomerDTO> findByName(String name) {
        Optional<List<Customer>> optionalcustomers = customerRepository.findByNameContainingIgnoreCase(name);
        if(optionalcustomers.isPresent()){
            List<Customer> customers = optionalcustomers.get();
            List<CustomerDTO> customersDTO = new ArrayList<>();
            customers.forEach(c -> customersDTO.add(this.convertToDTO(c)));
            return customersDTO;
        }else{
            throw new ResourceNotFoundException("nameCateg");
        }
    }

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        Customer s = new Customer();
        s = convertToCustomer(s, dto);
        return convertToDTO(customerRepository.save(s));
    }

    @Override
    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer s = new Customer();

        if(!customerRepository.existsById(id)){
            throw new ResourceNotFoundException("El Id: "+id+" de Customer NO existe!");
        }

        s = customerRepository.findById(id).get();
        s = convertToCustomer(s, dto);
        
        return convertToDTO(customerRepository.save(s));
    }

    @Override
    public void delete(Long idcustomer) {
        try {
            if (customerRepository.existsById(idcustomer)) {
                customerRepository.deleteById(idcustomer);
            } else {
                throw new ResourceNotFoundException("El Customer con ID " + idcustomer + " NO existe.");
            }
        } catch (Exception e) {
            throw new ResourceDeleteException("Error al eliminar el Customer.", e);
        }        
    }

    private CustomerDTO convertToDTO(Customer s){
        CustomerDTO dto = new CustomerDTO();
        dto.setIdcustomer(s.getIdcustomer());
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

    private Customer convertToCustomer(Customer c, CustomerDTO dto){
        c.setIdcustomer(dto.getIdcustomer());
        c.setState(dto.getState());
        
        System.out.println("****--IDPERSON = "+dto.getIdperson()+" --****");
        Optional<Person> optionalperson = personrepository.findById(dto.getIdperson());
        if(optionalperson.isPresent()){
            c.setPerson(optionalperson.get());
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
            
            c.setPerson(person);             
        }
        System.out.println("****-- END convertToCustomer --****");        

        return c;
    }    
}
