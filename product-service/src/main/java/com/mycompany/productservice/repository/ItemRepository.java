package com.mycompany.productservice.repository;

import com.mycompany.productservice.entity.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
}
