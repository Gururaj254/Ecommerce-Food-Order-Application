package com.Gururaj.restaurant_service.controller;

import com.Gururaj.restaurant_service.entity.Restaurant;
import com.Gururaj.restaurant_service.repository.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    // Add a restaurant with basic validation
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().isBlank() ||
                restaurant.getAddress() == null || restaurant.getAddress().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Restaurant savedRestaurant = repository.save(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
    }

    // get All
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = repository.findAll();
        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 if empty
        }
        return ResponseEntity.ok(restaurants);
    }

    // Get restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete restaurant by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Restaurant not found");
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Restaurant deleted successfully");
    }
}
