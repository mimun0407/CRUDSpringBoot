package com.example.demo.config;

import com.example.demo.Model.Customer;
import com.example.demo.enums.Role;
import com.example.demo.repository.RepositoryCustomer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(RepositoryCustomer repositoryCustomer){
        return args -> {
            if (repositoryCustomer.findByEmail("admin").isEmpty()) {
                Set<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());
                Customer customer = new Customer();
                customer.setEmail("admin");
//                customer.setRoles(roles);
                customer.setName("admin");
                customer.setPassword(passwordEncoder.encode("admin"));
                repositoryCustomer.save(customer);
        log.info("Customer created");

            }
        };
    }

}
