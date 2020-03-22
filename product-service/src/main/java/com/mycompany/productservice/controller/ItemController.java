package com.mycompany.productservice.controller;

import com.mycompany.productservice.entity.Item;
import com.mycompany.productservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems(){
        List<Item> items = StreamSupport.stream(itemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createItem(@RequestBody Item item) {
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
