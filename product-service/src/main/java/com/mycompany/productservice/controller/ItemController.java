package com.mycompany.productservice.controller;

import com.mycompany.productservice.entity.Item;
import com.mycompany.productservice.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/item")
@CrossOrigin
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems(){
        List<Item> items = StreamSupport.stream(itemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        LOGGER.info("Fetched list of all items: {}", items);
        return ResponseEntity.ok(items);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createItem(@RequestBody Item item) {
        LOGGER.info("Creating item: {}", item);
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
