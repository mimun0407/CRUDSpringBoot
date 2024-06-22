package com.example.demo.service;

import com.example.demo.Dto.AuthenticationDto;
import com.example.demo.Response.AuthenticationResponse;

public interface IAuthentication {
    AuthenticationResponse authenticateCheck(AuthenticationDto authenticationDto);
}
