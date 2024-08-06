package com.example.OrderService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequestDto {
    private Long cartId;
}
