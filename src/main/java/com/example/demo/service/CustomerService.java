package com.example.demo.service;

import com.example.demo.Dto.CustomerUpdateDTO;
import com.example.demo.Model.Customer;
import com.example.demo.Dto.CustomerCreateDto;
import com.example.demo.Response.CustomerResponse;
import com.example.demo.enums.Role;
import com.example.demo.repository.RepositoryCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomer{
    private final RepositoryCustomer repositoryCustomer;
    @Autowired
    public CustomerService(RepositoryCustomer repositoryCustomer) {
        this.repositoryCustomer = repositoryCustomer;
    }
    @PreAuthorize("hasRole('ADMIN')")
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
        List<String> roles=new ArrayList<>();
        roles.add(Role.USER.name());
//        customer.setRoles(roles);
        repositoryCustomer.save(customer);

        return customer;
    }

    @Override
    public void remove(String id) {
        repositoryCustomer.deleteById(id);
    }

    @PostAuthorize("returnObject.email == authentication.name")
    @Override
    public CustomerResponse findById(String id) {
        Customer customer = repositoryCustomer.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(id);
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setName(customer.getName());
        customerResponse.setPhoneNumber(customer.getPhoneNumber());
//        List<String> roles=customer.getRoles();
//        customerResponse.setRoles(roles);
        return customerResponse;
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
