package com.example.OrderService.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartDto {
        private Long id;

        private Long user_id;

        List<CartItemDto> cartItemList;


        public Double getTotalAmount(List<CartItemDto> cartItemList) {
            if (cartItemList == null) {
                return 0.0;
            }
            return cartItemList.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
        }
    }


