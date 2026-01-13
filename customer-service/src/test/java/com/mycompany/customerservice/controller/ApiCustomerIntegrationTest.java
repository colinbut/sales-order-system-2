package com.mycompany.customerservice.controller;

import com.mycompany.customerservice.JwtTestUtils;
import com.mycompany.customerservice.model.Address;
import com.mycompany.customerservice.model.Customer;
import com.mycompany.customerservice.repository.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = ApiCustomerIntegrationTest.Initializer.class)
public class ApiCustomerIntegrationTest {

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>();

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword(),
                    "spring.datasource.platform=mysql",
                    "spring.jpa.hibernate.ddl-auto=create"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreateCustomers(){
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .body(new HashMap<String, Object>(){
                        {
                            put("firstName", "Colin");
                            put("lastName", "But");
                            put("dateOfBirth", new Date());
                            put("email", "email@email.com");
                            put("address", address(
                                    123,
                                    "addressLine1",
                                    "addressLine2",
                                    "postcode",
                                    "Madrid",
                                    "Malden"));
                        }
                    })
                    .log().all()
                .when()
                    .post("http://localhost:8081/customer")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testGetCustomerById() {
        customerRepository.saveAll(Arrays.asList(
                customer("David", "Batton", new Date(), "email@email.com",
                        address(1, "addressLine1", "addressLine2", "postcode", "Cape Town", "Malden")),
                customer("Johnny", "Curtis", new Date(), "email@email.com",
                        address(34, "addressLine1", "addressLine2", "postcode", "Copenhagen", "Malden")),
                customer("Andre", "Schmelzer", new Date(), "email@email.com",
                        address(98, "addressLine1", "addressLine2", "postcode", "Munich", "Malden"))
        ));

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .log().all()
                .when()
                    .get("http://localhost:8081/customer/" + 2)
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("id", equalTo(2))
                    .body("firstName", equalTo("Johnny"))
                    .body("lastName", equalTo("Curtis"))
                    .body("email", equalTo("email@email.com"))
                    .body("address.houseFlatNo", equalTo(34))
                    .body("address.addressLine1", equalTo("addressLine1"))
                    .body("address.addressLine2", equalTo("addressLine2"))
                    .body("address.postCode", equalTo("postcode"))
                    .body("address.city", equalTo("Copenhagen"))
                    .body("address.county", equalTo("Malden"));

    }

    public Customer customer(String firstName, String lastName, Date dateOfBirth, String email, Address address) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setDateOfBirth(dateOfBirth);
        customer.setEmail(email);
        customer.setAddress(address);
        return customer;
    }

    private Address address(int houseFlatNo, String addressLine1, String addressLine2, String postCode, String city, String county){
        Address address = new Address();
        address.setHouseFlatNo(houseFlatNo);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setPostCode(postCode);
        address.setCity(city);
        address.setCounty(county);
        return address;
    }
}
