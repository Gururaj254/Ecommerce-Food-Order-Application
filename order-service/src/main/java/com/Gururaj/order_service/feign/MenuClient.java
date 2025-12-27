package com.Gururaj.order_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//feign client
@FeignClient(name = "menu-service")
public interface MenuClient {

    @GetMapping("/menus/restaurant/{restaurantId}")
    List<MenuDTO> getMenuByRestaurant(@PathVariable Long restaurantId);

}
