package user.example.user.feign;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.CartItem;

import java.util.List;

@FeignClient(name = ("cartitem"), url = "http://localhost:8083/cart-items")
public interface CartItemInterface  {

    @PostMapping
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItem cartItem);

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItem>> getItemsInCart(@PathVariable Long cartId) ;

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItem cartItem) ;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId);
}
