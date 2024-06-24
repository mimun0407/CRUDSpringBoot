package com.example.demo.repository;

import com.example.demo.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Customer, String >{
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
