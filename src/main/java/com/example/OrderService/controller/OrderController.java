package com.example.OrderService.controller;

import com.example.OrderService.dtos.CreateOrderRequestDto;
import com.example.OrderService.model.Order;
import com.example.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public ResponseEntity<List<Order>> orderHistory(@PathVariable("id") Long user_id) throws Exception {
        List<Order> orderList = orderService.viewOrdersFromPast(user_id);
          return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto,
                                              @RequestHeader (HttpHeaders.AUTHORIZATION) String token)  {
        try {
            orderService.processOrderFromCart(createOrderRequestDto.getCartId(),token);
            return new ResponseEntity<>("Order has been created", HttpStatus.OK);
        }catch (Exception e){
           return new ResponseEntity<>("An error occurred while processing Order",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
