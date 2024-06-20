package com.example.demo.service;

import com.example.demo.Dto.CustomerUpdateDTO;
import com.example.demo.Model.Customer;
import com.example.demo.Dto.CustomerCreateDto;
import com.example.demo.repository.RepositoryCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomer{
    private final RepositoryCustomer repositoryCustomer;
    @Autowired
    public CustomerService(RepositoryCustomer repositoryCustomer) {
        this.repositoryCustomer = repositoryCustomer;
    }
    @Override
    public Iterable<Customer> findAll() {
        return repositoryCustomer.findAll();
    }

    @Override
    public Customer save(CustomerCreateDto customerDto) {
        if (repositoryCustomer.existsByEmail(customerDto.getEmail())){
            throw new RuntimeException("Email Already Exists");
        }
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        repositoryCustomer.save(customer);
        return customer;
    }

    @Override
    public void remove(String id) {
        repositoryCustomer.deleteById(id);
    }

    @Override
    public Customer findById(String id) {
        return repositoryCustomer.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
    }

    @Override
    public Customer updateCustomer(String id, CustomerUpdateDTO customerDto) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setPassword(customerDto.getPassword());
        repositoryCustomer.save(customer);
        return customer;
    }
}
