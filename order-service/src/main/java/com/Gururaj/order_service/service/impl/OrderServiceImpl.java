package com.Gururaj.order_service.service.impl;//package com.Gururaj.order_service.service.impl;
import com.Gururaj.order_service.entity.Order;
import com.Gururaj.order_service.feign.MenuClient;
import com.Gururaj.order_service.feign.MenuDTO;
import com.Gururaj.order_service.repository.OrderRepository;
import com.Gururaj.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuClient menuClient;

    public OrderServiceImpl(OrderRepository orderRepository,
                            MenuClient menuClient) {
        this.orderRepository = orderRepository;
        this.menuClient = menuClient;
    }

    @Override
    @CircuitBreaker(name = "menuService", fallbackMethod = "placeOrderFallback")
    public Order placeOrder(Order order) {

        List<MenuDTO> menus =
                menuClient.getMenuByRestaurant(order.getRestaurantId());

        menus.stream()
                .filter(menu -> menu.getId().equals(order.getMenuId()))
                .findFirst()
                .ifPresent(menu -> {
                    order.setItemName(menu.getItemName());
                    order.setTotalPrice(menu.getPrice() * order.getQuantity());
                    order.setStatus("PLACED");
                });

        return orderRepository.save(order);
    }

    // fallback (simple, no exception handling)
    public Order placeOrderFallback(Order order, Throwable ex) {
        order.setItemName("UNKNOWN ITEM");
        order.setStatus("PENDING_MENU_SERVICE");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
