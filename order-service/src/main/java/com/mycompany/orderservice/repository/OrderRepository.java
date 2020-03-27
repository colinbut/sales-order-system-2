package com.mycompany.orderservice.repository;

import com.mycompany.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrderByCustomerId(String customerId);
}
