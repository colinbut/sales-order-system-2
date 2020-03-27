package com.mycompany.customerservice;

import com.mycompany.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiCustomerIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Disabled
    public void testGetCustomers(){

    }
}
