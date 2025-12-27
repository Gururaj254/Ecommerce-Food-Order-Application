package com.Gururaj.order_service.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO {

    private Long id;

    private String itemName;

    private double price;

}
