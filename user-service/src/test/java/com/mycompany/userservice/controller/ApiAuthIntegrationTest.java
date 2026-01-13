package com.mycompany.userservice.controller;

import com.mycompany.userservice.model.User;
import com.mycompany.userservice.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class ApiAuthIntegrationTest {

    private static final String URL = "http://localhost:8080";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testSignUpNewUsers() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Daniel");
        requestBody.put("email", "daniel.but@email.com");
        requestBody.put("username", "danielbut1");
        requestBody.put("password", "password");
        requestBody.put("roles", new String[]{"ADMIN", "DEV"});

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .log().all()
                .when()
                    .post(URL + "/signup")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testBadRequestToSignUp() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Daniel");
        requestBody.put("email", "daniel.but@email.com");
        requestBody.put("username", "daniel1"); //not valid length of username
        requestBody.put("password", "password");
        requestBody.put("roles", new String[]{"ADMIN", "DEV"});

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .log().all()
                .when()
                    .post(URL + "/signup")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testAuthenticateSuccess() {

        User user = new User();
        user.setUsername("joebloggs1");
        user.setPassword(bCryptPasswordEncoder.encode("password1"));
        user.setName("Joe Bloggs");
        user.setEmail("joebloggs1@email.com");
        user.setRoles("ADMIN,DEV,TEST");
        userRepository.save(user);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", "joebloggs1");
        requestBody.put("password", "password1");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .log().all()
                .when()
                    .post(URL + "/authenticate")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testAuthenticationNotSuccessful() {
        User user = new User();
        user.setUsername("joebloggs1");
        user.setPassword(bCryptPasswordEncoder.encode("password1"));
        user.setName("Joe Bloggs");
        user.setRoles("ADMIN,DEV,TEST");
        userRepository.save(user);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", "joebloggs1");
        requestBody.put("password", "wrong_password");

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .log().all()
                .when()
                    .post(URL + "/authenticate")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
