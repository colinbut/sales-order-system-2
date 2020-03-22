package com.mycompany.orderservice.controller;

import com.mycompany.orderservice.entity.Order;
import com.mycompany.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private OrderRepository orderRepository;

    @GetMapping("/orders/list")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersForCustomer(@RequestParam(name = "customerId") String customerId){
        List<Order> customerOrders = orderRepository.findOrderByCustomerId(customerId);
        return ResponseEntity.ok(customerOrders);
    }

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody Order order){
        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
