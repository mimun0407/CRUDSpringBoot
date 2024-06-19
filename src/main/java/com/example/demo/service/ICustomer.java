package com.example.demo.service;

import com.example.demo.Dto.CustomerUpdateDTO;
import com.example.demo.Model.Customer;
import com.example.demo.Dto.CustomerCreateDto;

public interface ICustomer {
    Iterable<Customer> findAll();
    Customer save(CustomerCreateDto t);
    void remove(String id);
    Customer findById(String id);
    Customer updateCustomer(String id, CustomerUpdateDTO t);
}
