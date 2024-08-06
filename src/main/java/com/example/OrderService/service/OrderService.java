package com.example.OrderService.service;

import com.example.OrderService.client.RestClient;
import com.example.OrderService.dtos.CartDto;
import com.example.OrderService.dtos.CartItemDto;
import com.example.OrderService.model.Order;
import com.example.OrderService.model.OrderItem;
import com.example.OrderService.model.OrderStatus;
import com.example.OrderService.model.PaymentStatus;
import com.example.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void processOrderFromCart(Long cartId, String token) throws Exception {
        // Create a new Order object from the Cart data
        Object cartValidStatus = RestClient.validateCart(cartId,token);
        if (cartValidStatus.equals("Cart is not valid")) {
            throw new Exception("Cart not found");
        }

        RestTemplate restTemplate = new RestTemplate();
        String cartDetailsUrl = "http://localhost:8080/Cart/" + cartId; // Assuming there's an endpoint to get cart details
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); // Add the token to the headers

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the GET request with headers
        ResponseEntity<CartDto> response = restTemplate.exchange(cartDetailsUrl, HttpMethod.GET, entity, CartDto.class);
        CartDto cartDto = response.getBody();
        if (cartDto == null) {
            throw new Exception("Failed to retrieve cart details");
        }
        //Creating Order
        Order order = new Order();
        order.setUserId(cartDto.getUser_id());
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(cartDto.getTotalAmount(cartDto.getCartItemList()));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryAddress("Sahadatganj, Lucknow");

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItemDto cartItem : cartDto.getCartItemList()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);
        }
        order.setItems(orderItems);

        orderRepository.save(order);

        //Sending order to payment service
          RestClient.sendOrder(order);
    }

    public List<Order> viewOrdersFromPast(Long user_id) throws Exception {
       Optional<List<Order>> optionalOrderList = orderRepository.findAllByUserId(user_id);
       if(optionalOrderList.isEmpty()){
           throw new Exception("No past Orders");
       }
       return optionalOrderList.get();
    }
}
