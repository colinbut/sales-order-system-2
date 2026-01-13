package com.mycompany.userservice.controller;

import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ApiUserIntegrationTest {

    private static final String URL = "http://localhost:8080";

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testGetUsers() {
        User user = new User();
        user.setUsername("joebloggs1");
        user.setPassword(bCryptPasswordEncoder.encode("password1"));
        user.setName("Joe Bloggs");
        user.setEmail("joebloggs1@email.com");
        user.setRoles("ADMIN,DEV,TEST");
        userRepository.save(user);

        RestAssured
                .given()
                    .log().all()
                .when()
                    .get(URL + "/users/list")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", equalTo(3));
    }

    @Test
    public void testGetUsersByUsername() {
        User user = new User();
        user.setUsername("joebloggs1");
        user.setPassword(bCryptPasswordEncoder.encode("password1"));
        user.setName("Joe Bloggs");
        user.setEmail("joebloggs1@email.com");
        user.setRoles("ADMIN,DEV,TEST");
        userRepository.save(user);

        RestAssured
                .given()
                    .log().all()
                .when()
                    .get(URL + "/user?username=joebloggs1")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("name", equalTo("Joe Bloggs"))
                    .body("username", equalTo("joebloggs1"))
                    .body("password", notNullValue())
                    .body("email", equalTo("joebloggs1@email.com"))
                    .body("roles", contains("ADMIN", "DEV", "TEST"));
    }

}
