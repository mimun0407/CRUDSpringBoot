package com.example.demo.service;

import com.example.demo.Dto.CustomerUpdateDTO;
import com.example.demo.Model.Customer;
import com.example.demo.Dto.CustomerCreateDto;
import com.example.demo.Response.CustomerResponse;

public interface ICustomer {
    Iterable<Customer> findAll();
    Customer save(CustomerCreateDto t);
    void remove(String id);
    CustomerResponse findById(String id);
    Customer updateCustomer(String id, CustomerUpdateDTO t);
}
