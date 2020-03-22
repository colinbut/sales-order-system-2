package com.mycompany.productservice.repository;

import com.mycompany.productservice.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {
}
