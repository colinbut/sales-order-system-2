package com.mycompany.productservice.controller;

import com.mycompany.productservice.JwtTestUtils;
import com.mycompany.productservice.entity.Item;
import com.mycompany.productservice.repository.ItemRepository;
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
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = ApiProductIntegrationTest.Initializer.class)
public class ApiProductIntegrationTest {

    @Container
    public static GenericContainer<?> redisContainer = new GenericContainer<>("redis:5.0.3-alpine")
            .withExposedPorts(6379);

    static class Initializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.redis.host=" + redisContainer.getContainerIpAddress(),
                    "spring.redis.port=" + redisContainer.getFirstMappedPort()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateItem(){
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .body(new HashMap<String, Object>(){
                        {
                            put("name", "Banana");
                            put("price", BigDecimal.valueOf(1.25));
                        }
                    })
                    .log().all()
                .when()
                    .post("http://localhost:8083/item")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void testGetItems(){

        itemRepository.saveAll(Arrays.asList(
                item("Pears", BigDecimal.valueOf(1.20)),
                item("Grapes", BigDecimal.valueOf(2.10))
        ));

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .log().all()
                .when()
                    .get("http://localhost:8083/item/list")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", equalTo(2));
    }

    private Item item(String name, BigDecimal price) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        return item;
    }

}
