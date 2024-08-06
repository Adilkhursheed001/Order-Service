package com.example.OrderService.repository;

import com.example.OrderService.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {


    Order save(Order order);

    Optional<List<Order>> findAllByUserId(Long orders);
}
