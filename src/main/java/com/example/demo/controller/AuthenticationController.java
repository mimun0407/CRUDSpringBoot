package com.example.demo.controller;

import com.example.demo.Dto.AuthenticationDto;
import com.example.demo.Dto.IntrospectDto;
import com.example.demo.Response.AuthenticationResponse;
import com.example.demo.Response.IntrospectResponse;
import com.example.demo.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
     AuthenticationService authenticationService;
    @PostMapping("/log")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationDto authenticationDto){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse=authenticationService.authenticateCheck(authenticationDto);
        return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
    }
    @PostMapping("/valid")
    public ResponseEntity<IntrospectResponse> Valid(@RequestBody IntrospectDto introspectDto) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = new IntrospectResponse();
        introspectResponse=authenticationService.introspectResponse(introspectDto);
        return new ResponseEntity<>(introspectResponse,HttpStatus.OK);
    }
}
