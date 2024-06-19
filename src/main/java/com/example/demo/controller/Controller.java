package com.example.demo.controller;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Model.Customer;
import com.example.demo.service.ICustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Customer")
public class Controller {
    private final ICustomer customerService;

    @Autowired
    public Controller(ICustomer customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customerProfile/{id}")
    public ResponseEntity<Customer> getCustomerList(@PathVariable("id") String id) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customer) {
        Customer customer1 = customerService.save(customer);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Customer>> getCustomerList() {
        Iterable<Customer> customers = customerService.findAll();
        if (customers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<Customer> UpdateCustomer(@PathVariable String id, @RequestBody CustomerDto customer) {
        Customer customer1 = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(customer1, HttpStatus.CREATED);
    }
}
