package com.mycompany.orderservice.controller;

import com.mycompany.orderservice.entity.Order;
import com.mycompany.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/orders/list")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersForCustomer(@RequestParam(name = "customerId") String customerId){
        LOGGER.info("Getting orders for customer: {}", customerId);
        List<Order> customerOrders = orderRepository.findOrderByCustomerId(customerId);
        LOGGER.info("Fetched orders: {} for customer: {}", customerOrders, customerId);
        return ResponseEntity.ok(customerOrders);
    }

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody Order order){
        LOGGER.info("Creating new Order: {}", order);
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
