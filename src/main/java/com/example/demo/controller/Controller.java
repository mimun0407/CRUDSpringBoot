package com.example.demo.controller;

import com.example.demo.Dto.CustomerCreateDto;
import com.example.demo.Dto.CustomerUpdateDTO;
import com.example.demo.Model.Customer;
import com.example.demo.Response.CustomerResponse;
import com.example.demo.service.ICustomer;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer")
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private final ICustomer customerService;

    @Autowired
    public Controller(ICustomer customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customerProfile/{id}")
    public ResponseEntity<CustomerResponse> getCustomerList(@PathVariable("id") String id) {
        CustomerResponse customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerCreateDto customer) {
        Customer customer1 = customerService.save(customer);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Customer>> getCustomerList() {
        Iterable<Customer> customers = customerService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Email : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        if (customers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @PutMapping("/Update/{id}")
    public ResponseEntity<Customer> UpdateCustomer(@PathVariable String id, @RequestBody CustomerUpdateDTO customer) {
        Customer customer1 = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }
}
