package com.Gururaj.order_service.circuitbreaker;

import com.Gururaj.order_service.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class MenuServiceFallback {

    public Order fallbackPlaceOrder(Order order, Throwable ex) {

        // Log error
        System.out.println("Menu Service DOWN: " + ex.getMessage());

        order.setItemName("UNKNOWN ITEM");
        order.setStatus("PENDING_MENU_SERVICE");


        return order;
    }
}
