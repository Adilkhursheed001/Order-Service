package com.example.OrderService.client;


import com.example.OrderService.model.Order;
import com.example.OrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class RestClient {

    @Autowired
    private static OrderRepository orderRepository;

        public static void sendOrder(Order order) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
            String paymentServiceUrl = "http://localhost:9001/Payment/"; // Replace with actual URL of the payment service

            // Send request to Payment service
            try {
                ResponseEntity<String> response = restTemplate.postForEntity(paymentServiceUrl,  order , String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
                     orderRepository.save(order);
                } else {
                    throw new Exception("Failed to connect to payment sevice");
                }
            } catch (Exception e) {
                throw new Exception("Error while processing : " + e.getMessage());
            }
        }
        public static Object validateCart(Long cartId , String token){
            RestTemplate restTemplate = new RestTemplate();
            String cartServiceUrl = "http://localhost:8080/Cart/validate/"+ cartId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token); // Add the token to the headers

            // Create an HttpEntity with the headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

          //  ResponseEntity<String> response = restTemplate.getForEntity(cartServiceUrl, String.class);
            ResponseEntity<Object> response = restTemplate.exchange(cartServiceUrl, HttpMethod.GET, entity,Object.class);
            // Handle the response based on the status code and body
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // This will return "Cart is Valid" or "Cart is not valid"
            } else {
                throw new RuntimeException("Failed to validate cart. HTTP Status: " + response.getStatusCode());
            }
        }
        }







