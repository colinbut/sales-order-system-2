package com.mycompany.orderservice.controller;

import com.mycompany.orderservice.JwtTestUtils;
import com.mycompany.orderservice.entity.Order;
import com.mycompany.orderservice.repository.OrderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiOrderIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateOrder() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .body(new HashMap<String, Object>(){
                        {
                            put("customerId", "1");
                            put("items", Arrays.asList("Banana", "Pears"));
                        }
                    })
                    .log().all()
                .when()
                    .post("http://localhost:8082/order")
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
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .log().all()
                .when()
                    .get("http://localhost:8082/orders/list")
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
                    .headers("Authorization", "Bearer " + JwtTestUtils.getJwtRequestHeader())
                    .log().all()
                .when()
                    .get("http://localhost:8082/orders?customerId=1")
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
