package com.mycompany.customerservice.controller;

import com.mycompany.customerservice.model.Customer;
import com.mycompany.customerservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @PostMapping
    public void createCustomer(@RequestBody Customer customer){
        LOGGER.info("Creating customer: {}", customer);
        customerRepository.save(customer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Integer customerId) {
        return ResponseEntity.ok(customerRepository.findById(customerId).orElseThrow(RuntimeException::new));
    }
}
