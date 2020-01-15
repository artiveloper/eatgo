package com.example.eatgo.service;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.domain.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Transactional
    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        update(restaurantId, menuItems);
    }

    private void update(Long restaurantId, List<MenuItem> menuItems) {
        for (MenuItem menuItem : menuItems) {
            if (menuItem.isDestroy()) {
                menuItemRepository.deleteById(menuItem.getId());
                return;
            }
            menuItem.setRestaurantId(restaurantId);
            menuItemRepository.save(menuItem);
        }
    }

}
