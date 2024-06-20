package com.example.demo.controller;

import com.example.demo.Dto.AuthenticationDto;
import com.example.demo.Response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
     AuthenticationService authenticationService;
    @PostMapping("/log")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationDto authenticationDto){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        boolean check=  authenticationService.authenticateCheck(authenticationDto);
        authenticationResponse.setAuthenticated(check);
        return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
    }
}
