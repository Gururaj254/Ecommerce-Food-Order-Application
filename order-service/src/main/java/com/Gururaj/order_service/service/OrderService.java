package com.Gururaj.order_service.service;

import com.Gururaj.order_service.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Order order);

    List<Order> getAllOrders();

}
