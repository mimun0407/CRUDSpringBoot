package com.example.demo.service;

import com.example.demo.Dto.AuthenticationDto;
import com.example.demo.Model.Customer;
import com.example.demo.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthentication{
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Override
    public boolean authenticateCheck(AuthenticationDto authenticationDto) {
         Optional<Customer> customer = Optional.ofNullable(authenticationRepository.findByEmail(authenticationDto.getEmail()).orElseThrow(() -> new RuntimeException("Email Not Found"))
         );
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.matches(authenticationDto.getPassword(), customer.get().getPassword());
    }

}
