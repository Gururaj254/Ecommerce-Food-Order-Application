package com.Gururaj.menu_service.controller;

import com.Gururaj.menu_service.entity.Menu;
import com.Gururaj.menu_service.repository.MenuRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @PostMapping
    public Menu addMenu(@RequestBody Menu menu) {
        return menuRepository.save(menu);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<Menu> getMenuByRestaurant(@PathVariable Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId);
    }
}

