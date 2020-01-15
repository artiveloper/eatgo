package com.example.eatgo.interfaces;

import com.example.eatgo.domain.MenuItem;
import com.example.eatgo.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping("/restaurants/{restaurantId}/menuitems")
    public ResponseEntity<?> list(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.getMenuItems(restaurantId);
        return ResponseEntity.ok().body(menuItems);
    }

    @PatchMapping("/restaurants/{restaurantId}/menuitems")
    public ResponseEntity<?> bulkUpdate(@PathVariable Long restaurantId, @RequestBody List<MenuItem> menuItems) {
        menuItemService.bulkUpdate(restaurantId, menuItems);
        return ResponseEntity.ok().body("{}");
    }

}
