package com.mycompany.orderservice.config;

import com.mycompany.orderservice.entity.Order;
import com.mycompany.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Profile({"local", "dev", "staging", "prod"})
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private OrderRepository orderRepository;

    @PostConstruct
    public void init(){
        LOGGER.info("Populating MongoDB with some sample orders");
        orderRepository.saveAll(Arrays.asList(
                order("1", Arrays.asList("Pink Lady Apple", "Apple iPad Mini - 4th Gen")),
                order("2", Arrays.asList("Fingers", "Clubs Orange", "Onions")),
                order("2", Arrays.asList("Fingers", "Clubs Orange", "Bottle of Water Evian")),
                order("3", Collections.singletonList("Samsung TV 42-inch Smart TV"))
        ));
    }

    private Order order(String customerId, List<String> items){
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItems(items);
        return order;
    }
}
