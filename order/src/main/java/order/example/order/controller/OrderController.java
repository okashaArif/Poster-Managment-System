package order.example.order.controller;


import order.example.order.dto.OrderRequest;
import order.example.order.model.Order_child;
import order.example.order.repository.OrderRepository;
import order.example.order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.Order;
import org.utils.model.User;


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
    public ResponseEntity<Order_child> createOrder(@RequestBody OrderRequest orderRequest) {
        // Get the user from the database using the userId
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new Order and set its properties
        Order_child order = new Order_child();
        order.setUser(user);  // Set the user object
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setStatus("PENDING");
        order.setOrderDate(new Date());  // Set the current date as the order date

        // Save the order
        Order_child savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status); // Update the order status
            Order updatedOrder = orderRepository.save(order); // Save the updated order
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build(); // If the order is not found
        }
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
    public ResponseEntity<Order_child> updateOrder(@PathVariable Long orderId, @RequestBody Order_child order) {
        order.setId(orderId);
        Order_child updatedOrder = orderRepository.save(order);
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