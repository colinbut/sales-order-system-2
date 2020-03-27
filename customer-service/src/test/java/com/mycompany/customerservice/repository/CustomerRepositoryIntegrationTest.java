package com.mycompany.customerservice.repository;

import com.mycompany.customerservice.model.Address;
import com.mycompany.customerservice.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSavingNewCustomers(){
        customerRepository.save(
                customer("Colin", "But",
                        Date.from(LocalDate.of(2020, 2, 20).atStartOfDay().toInstant(ZoneOffset.UTC)), "email@email.com",
                        address(1, "Amazing Park", "Runaway", "GE2 JUI", "Madrid", "Malden"))
        );

        Optional<Customer> savedCustomer = customerRepository.findById(1);

        if (savedCustomer.isPresent()) {
            Customer customer = savedCustomer.get();
            assertEquals("Colin", customer.getFirstName());
            assertEquals("But", customer.getLastName());
            assertEquals("email@email.com", customer.getEmail());
        } else {
            fail("Customer failed to save!");
        }
    }

    private Customer customer(String firstName, String lastName, Date dateOfBirth, String email, Address address){
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDateOfBirth(dateOfBirth);
        customer.setEmail(email);
        customer.setAddress(address);
        return customer;
    }

    private Address address(int houseFlatNo, String addressLine1, String addressLine2, String postcode, String city, String county){
        Address address = new Address();
        address.setHouseFlatNo(houseFlatNo);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setPostCode(postcode);
        address.setCity(city);
        address.setCounty(county);
        return address;
    }
}
