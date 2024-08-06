package com.example.OrderService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Double totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private PaymentStatus paymentStatus;
    private String deliveryAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

}
