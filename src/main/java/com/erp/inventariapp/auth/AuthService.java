package com.erp.inventariapp.auth;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.PersonDTO;
import com.erp.inventariapp.entities.Person;
import com.erp.inventariapp.services.JwttService;
import com.erp.inventariapp.entities.UserApp;
import com.erp.inventariapp.enums.RoleUserEnum;
import com.erp.inventariapp.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwttService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = (UserDetails) userRepository.findByUsername(request.getUsername()).orElseThrow();
        
        /** Para obtener el rol */
        UserApp u = new UserApp();
        String roleUser = "ADMIN";
        Optional<UserApp> opt_userApp = userRepository.findByUsername(request.getUsername());
        if(opt_userApp.isPresent()){
            u = opt_userApp.get();
            roleUser = u.getRoleUser().toString();
            System.out.println("****** ROL DE USER: "+roleUser+" ******");
        }

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .username(user.getUsername())
            .roleUser(roleUser)
            .build(); 

        //UserApp user = (UserApp) userRepository.findByUsername(request.getUsername()).orElseThrow();
        //return new AuthResponse(token, user.getUsername(), RoleUserEnum.ADMIN.toString());           
    }

    public AuthResponse register(RegisterRequest request){
        Person p = new Person();
        p = convertToPerson(p, request.getPerson());

        UserApp user = UserApp.builder()
                    .username(request.username)
                    .password(passwordEncoder.encode(request.password))
                    .roleUser(RoleUserEnum.CUSTOMER)
                    .state(request.state)
                    .person(p)
                    .build();
        
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    private Person convertToPerson(Person p, PersonDTO dto) {
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
