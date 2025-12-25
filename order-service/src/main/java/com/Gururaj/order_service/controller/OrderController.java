package com.Gururaj.order_service.controller;

import com.Gururaj.order_service.entity.Order;
import com.Gururaj.order_service.feign.MenuClient;
import com.Gururaj.order_service.feign.MenuDTO;
import com.Gururaj.order_service.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final MenuClient menuClient;

    public OrderController(OrderRepository orderRepository, MenuClient menuClient) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
    }

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {

        // Fetch menu items from Menu Service
        List<MenuDTO> menus = menuClient.getMenuByRestaurant(order.getRestaurantId());

        // Find the menu item selected by user
        MenuDTO menu = menus.stream()
                .filter(m -> m.getId().equals(order.getMenuId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        // Fill order fields
        order.setMenuId(menu.getId());            //  sets item_id in DB
        order.setItemName(menu.getItemName());
        order.setPrice(menu.getPrice());
        order.setTotalPrice(menu.getPrice() * order.getQuantity());
        order.setStatus("PLACED");

        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
