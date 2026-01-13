package com.mycompany.productservice.config;

import com.mycompany.productservice.entity.Item;
import com.mycompany.productservice.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;

@Component
@Profile({"local", "dev", "prod"}) // right now this is a some sort of a hack to prevent Integration Tests running this ApplicationConfig
public class ApplicationConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void init(){
        itemRepository.saveAll(Arrays.asList(
                item("Fingers", BigDecimal.valueOf(1.00)),
                item("Clubs Orange", BigDecimal.valueOf(0.99)),
                item("Onions", BigDecimal.valueOf(0.99)),
                item("Bottle of Water Evian", BigDecimal.valueOf(1.45)),
                item("Pink Lady Apple", BigDecimal.valueOf(2.00)),
                item("Apple iPad Mini - 4th Gen", BigDecimal.valueOf(399.99)),
                item("Samsung TV 42-inch Smart TV", BigDecimal.valueOf(445.00))
        ));
        LOGGER.info("Populated Redis data-store with sample data during start-up");
    }

    private Item item(String name, BigDecimal price){
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        return item;
    }
}
