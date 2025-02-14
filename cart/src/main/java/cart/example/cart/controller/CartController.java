package cart.example.cart.controller;


import cart.example.cart.model.CartItem_child;
import cart.example.cart.model.Cart_child;
import cart.example.cart.repository.CartItemRepository;
import cart.example.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.Cart;
import org.utils.model.CartItem;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping
    public ResponseEntity<Cart_child> createCart(@RequestBody Cart_child cart) {
        Cart_child savedCart = cartRepository.save(cart);
        return ResponseEntity.ok(savedCart);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        System.out.println(cartRepository.findAll());
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{userId}")
    @Transactional
    public ResponseEntity<Object> getCart(@PathVariable Long userId) {
        System.out.println(userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);

        // Check if a Cart exists for the given userId
        if (optionalCart.isPresent() ) {
            Cart cart =  optionalCart.get(); // Get the Cart object
            System.out.println("Cart  " + cart);
            System.out.println("Cart items: " + cart.getItems()); // Debugging output

            // Check if the Cart has items
            if (cart.getItems() == null || cart.getItems().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No items in the cart.");
            }

            // Return the Cart with its items
            return ResponseEntity.ok(cart); // cart contains items as well
        } else {
            System.out.println("error  ");
            return ResponseEntity.notFound().build();
        }
    }





    @PutMapping("/{cartId}")
    public ResponseEntity<Cart_child> updateCart(@PathVariable Long cartId, @RequestBody Cart_child cart) {
        cart.setId(cartId);
        Cart_child updatedCart = cartRepository.save(cart);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartRepository.deleteById(cartId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<String> checkoutCart(@PathVariable Long cartId) {
        // Checkout logic here
        return ResponseEntity.ok("Checkout successful");
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            double total = cart.getItems().stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
            return ResponseEntity.ok(total);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> listCartItems(@PathVariable Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            return ResponseEntity.ok(cart.getItems());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cartId}/item/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        CartItem_child item = cartItemRepository.findByCartIdAndProductId(cartId, productId);
        if (item != null) {
            cartItemRepository.delete(item);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}