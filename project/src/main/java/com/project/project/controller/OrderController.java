package com.project.project.controller;

import com.project.project.dto.OrderRequest;
import com.project.project.model.Order;
import com.project.project.model.User;
import com.project.project.repository.OrderRepository;
import com.project.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        // Get the user from the database using the userId
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Order and set its properties
        Order order = new Order();
        order.setUser(user);  // Set the user object
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setStatus("PENDING");
        order.setOrderDate(new Date());  // Set the current date as the order date

        // Save the order
        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }




    @GetMapping
    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) {
        order.setId(orderId);
        Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderRepository.deleteById(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId); // Implement this method in the repository
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{orderId}/refund")
    public ResponseEntity<String> requestRefund(@PathVariable Long orderId) {
        // Refund logic here
        return ResponseEntity.ok("Refund requested");
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmDelivery(@PathVariable Long orderId) {
        // Confirm delivery logic here
        return ResponseEntity.ok("Delivery confirmed");
    }

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<String> markAsShipped(@PathVariable Long orderId) {
        // Shipping logic here
        return ResponseEntity.ok("Order marked as shipped");
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> markAsCanceled(@PathVariable Long orderId) {
        // Canceling logic here
        return ResponseEntity.ok("Order marked as canceled");
    }
}