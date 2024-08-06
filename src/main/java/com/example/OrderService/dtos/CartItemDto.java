package com.example.OrderService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long productId;
    private Integer quantity;
    private Double price;
}
