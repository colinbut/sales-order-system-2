package com.mycompany.customerservice.config;

import com.mycompany.customerservice.model.Address;
import com.mycompany.customerservice.model.Customer;
import com.mycompany.customerservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;

@Component
@Profile("!test")
public class ApplicationStartUpListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStartUpListener.class);

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void init(){
        LOGGER.info("Application Started! - Populating MySQL with some sample customers");
        customerRepository.saveAll(Arrays.asList(
                customer("Jonathan", "Wang", getDob(2003, 12, 1), "email1@email.com",
                        address(100, "addressLine1", "addressLine6", "KI4 89Y", "Dubai", "county1")),
                customer("Oppa", "Wing", getDob(1998, 4, 18), "email2@email.com",
                        address(234, "addressLine2", "addressLine7", "GH3 9HY", "Sydney", "county2")),
                customer("Focus", "Out", getDob(1997, 2, 14), "email3@email.com",
                        address(1098, "addressLine3", "addressLine8", "SW3 90G", "New York", "county3")),
                customer("Wilson", "Ying", getDob(1993, 7, 21), "email4@email.com",
                        address(23, "addressLine4", "addressLine9", "LP1 90D", "Beijing", "county4")),
                customer("Mandy", "Yeung", getDob(1999, 8, 30), "email5@email.com",
                        address(87, "addressLine5", "addressLine10", "ZE4 JK7", "Berlin", "county5"))
        ));
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

    private Date getDob(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay().toInstant(ZoneOffset.UTC));
    }

}
