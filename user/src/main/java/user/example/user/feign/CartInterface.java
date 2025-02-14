package user.example.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.Cart;
import org.utils.model.CartItem;

import java.util.List;

@FeignClient(name = ("cart"), url = "http://localhost:8083/cart")
public interface CartInterface  {
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) ;

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() ;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getCart(@PathVariable Long userId) ;


    @PutMapping("/{cartId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, @RequestBody Cart cart);

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable Long cartId) ;

    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable Long cartId);

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> listCartItems(@PathVariable Long cartId) ;

    @DeleteMapping("/{cartId}/item/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId);
}
