//package com.example.OrderService.service;
//
//import com.example.OrderService.model.Cart;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OrderListenerService {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @KafkaListener(topics = "placeOrder", groupId = "order-service-group")
//    public void consume(String message) {
//        try {
//            // Deserialize the cart data
//            Cart cart = objectMapper.readValue(message, Cart.class);
//            // Process the cart to create an order
//            orderService.processOrderFromCart(cart);
//        } catch (Exception e) {
//            // Handle exceptions (e.g., logging, error handling)
//            e.printStackTrace();
//        }
//    }
//}