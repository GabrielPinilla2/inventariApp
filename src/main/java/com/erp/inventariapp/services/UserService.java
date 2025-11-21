package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.UserDTO;
import com.erp.inventariapp.entities.Person;
import com.erp.inventariapp.entities.UserApp;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.UserRepository;
import com.erp.inventariapp.repositories.PersonRepository;
import com.erp.inventariapp.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userrepository;
   
    @Autowired
    PersonRepository personrepository;

    @Autowired
    PersonService personservice;

    @Override
    public List<UserDTO> findAll() {
        List<UserApp> users = (List<UserApp>) userrepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        users.forEach(u -> usersDTO.add(this.convertToDTO(u)));
        return usersDTO;
    }

    @Override
    public UserDTO findById(Long iduser) {
        UserApp u = userrepository.findById(iduser).get();
        return (this.convertToDTO(u)); 
    }


    @Override
    public List<UserDTO> findByName(String name) {
        Optional<List<UserApp>> optionalusers = userrepository.findByNameContainingIgnoreCase(name);
        if(optionalusers.isPresent()){
            List<UserApp> users = optionalusers.get();
            List<UserDTO> usersDTO = new ArrayList<>();
            users.forEach(c -> usersDTO.add(this.convertToDTO(c)));
            return usersDTO;
        }else{
            throw new ResourceNotFoundException("nameUser");
        }
    }

    @Override
    public UserDTO create(UserDTO dto) {
        UserApp u = new UserApp();
        return saveOrUpdate(u, dto);
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        UserApp u = userrepository.findById(id).get();
        return saveOrUpdate(u, dto);
    }

    @Override
    public void delete(Long iduser) {
        if (!userrepository.existsById(iduser))
            throw new ResourceNotFoundException("Usuario no encontrado");
        userrepository.deleteById(iduser);
    }

    private UserDTO saveOrUpdate(UserApp u, UserDTO dto) {
        //Person person = modelMapper.map(dto.getPerson(), Person.class);

        u.setUsername(dto.getUsername());
        
        //Si Usuario es nuevo si trae password para encriptar
        if(u.getIduser()==null){
            //u.setPassword(dto.getPassword());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(dto.getPassword());
            u.setPassword(hashedPassword); // Guarda el hash, no el texto plano
            //Para verificar contraseñas al Iniciar Sesión
            //boolean matches = encoder.matches(enteredPassword, userFromDB.getPassword());
        }
        
        u.setRoleUser(dto.getRoleUser());
        u.setState(dto.getState());
        System.out.println("****--IDPERSON = "+dto.getIdperson()+" --****");
        Optional<Person> optionalperson = personrepository.findById(dto.getIdperson());
        if(optionalperson.isPresent()){
            u.setPerson(optionalperson.get());
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
            
            u.setPerson(person);             
        }
        
        System.out.println("****-- END SAVEORCREATE --****");
        
        return convertToDTO(userrepository.save(u));
    }

    private UserDTO convertToDTO(UserApp u) {
        UserDTO dto = new UserDTO();
        dto.setIduser(u.getIduser());
        dto.setUsername(u.getUsername());
        dto.setPassword(u.getPassword());
        dto.setRoleUser(u.getRoleUser());
        dto.setState(u.getState());
        
        if(u.getPerson()!=null){
            dto.setIdperson(u.getPerson().getIdperson());
            dto.setTypeId(u.getPerson().getTypeId());
            dto.setIdentification(u.getPerson().getIdentification());
            dto.setName(u.getPerson().getName());
            dto.setAdress(u.getPerson().getAdress());
            dto.setEmail(u.getPerson().getEmail());
            dto.setPhone(u.getPerson().getPhone());
            dto.setBirthdate(u.getPerson().getBirthdate());
            dto.setGenre(u.getPerson().getGenre());
        }else{
            dto.setIdperson(null);
        }

        return dto;
    }    
}
