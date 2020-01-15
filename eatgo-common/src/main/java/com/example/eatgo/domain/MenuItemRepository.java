package com.example.eatgo.domain;

import com.example.eatgo.domain.MenuItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuItemRepository extends CrudRepository<MenuItem, Long> {

    List<MenuItem> findAllByRestaurantId(Long id);

}
