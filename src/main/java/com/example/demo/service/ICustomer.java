package com.example.demo.service;

import com.example.demo.Model.Customer;
import com.example.demo.Dto.CustomerDto;

import java.util.Optional;

public interface ICustomer {
    Iterable<Customer> findAll();
    Customer save(CustomerDto t);
    void remove(String id);
    Customer findById(String id);
    Customer updateCustomer(String id, CustomerDto t);
}
