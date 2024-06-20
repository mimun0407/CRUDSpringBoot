package com.example.demo.service;

import com.example.demo.Dto.AuthenticationDto;

public interface IAuthentication {
    boolean authenticateCheck(AuthenticationDto authenticationDto);
}
