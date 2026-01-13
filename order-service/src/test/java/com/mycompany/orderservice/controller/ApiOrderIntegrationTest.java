package com.mycompany.orderservice.controller;

import com.mycompany.orderservice.JwtTestUtils;
import com.mycompany.orderservice.entity.Order;
import com.mycompany.orderservice.repository.OrderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
public class ApiOrderIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiOrderIntegrationTest.class);

    @Container
    static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:6.0.4")
        .withStartupAttempts(3)
        .withStartupTimeout(Duration.ofSeconds(120))
        .withCreateContainerCmdModifier(cmd -> cmd.withPlatform("linux/amd64"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        String mongoUri = mongoDbContainer.getReplicaSetUrl();
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
        LOGGER.error("Setting spring.data.mongodb.uri to: {}", mongoUri);
    }

    @Autowired
    private OrderRepository orderRepository;

    @LocalServerPort
    private int port;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @BeforeEach
    public void setupDb() {
        orderRepository.deleteAll();
    }

    @Test
    public void testCreateOrder() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader(jwtSecret))
                    .body(new HashMap<String, Object>(){
                        {
                            put("customerId", "1");
                            put("items", Arrays.asList("Banana", "Pears"));
                        }
                    })
                    .log().all()
                .when()
                    .post("/order")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testGetOrders() {

        orderRepository.saveAll(Arrays.asList(
                order("1", Arrays.asList("Banana", "Oranges", "Apples")),
                order("2", Arrays.asList("iPhone 8", "iPad 4 Mini", "MacBook Pro")),
                order("3", Arrays.asList("Pen", "Highlighter", "Cups"))
        ));

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader(jwtSecret))
                    .log().all()
                .when()
                    .get("/orders/list")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", equalTo(3));
    }

    @Test
    public void testGetOrdersForCustomer() {
        orderRepository.save(order("1", Arrays.asList("Book", "Notepad")));

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader(jwtSecret))
                    .log().all()
                .when()
                    .get("/orders?customerId=1")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", equalTo(1));
    }

    private Order order(String customerId, List<String> items){
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItems(items);
        return order;
    }

}
