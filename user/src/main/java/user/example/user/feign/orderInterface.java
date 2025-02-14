package user.example.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.Order;
import user.example.user.dto.OrderRequest;


import java.util.Date;
import java.util.List;

@FeignClient(name = ("order"), url = "http://localhost:8081/orders")
public interface orderInterface  {
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest);

    @GetMapping
    public List<Order> listOrders();

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) ;

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order order) ;

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) ;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) ;


}
