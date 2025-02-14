package cart.example.cart.controller;


import cart.example.cart.model.CartItem_child;
import cart.example.cart.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping
    public ResponseEntity<CartItem_child> addItemToCart(@RequestBody CartItem_child cartItem) {
        try {
            System.out.println("Received CartItem: " + cartItem);
            // Validate the CartItem input
            if (cartItem == null || cartItem.getProduct() == null || cartItem.getQuantity() == null || cartItem.getQuantity() <= 0 || cartItem.getPrice() == null || cartItem.getPrice() <= 0) {
                return ResponseEntity.badRequest().body(null); // Return 400 Bad Request with error details
            }

            // Optionally, you could also check if the Cart and Product exist in the database
            // Example:
            if (cartItem.getCart() == null || cartItem.getCart().getCartId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            // Save the cartItem to the database
            CartItem_child savedItem = cartItemRepository.save(cartItem);

            // Return a response with 201 Created status, and a location header for the saved resource
            return ResponseEntity.ok(savedItem);

        } catch (Exception e) {
            // Log the exception and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItem_child>> getItemsInCart(@PathVariable Long cartId) {
        List<CartItem_child> items = cartItemRepository.findByCartId(cartId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem_child> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItem_child cartItem) {
        cartItem.setCartItemId(cartItemId);
        CartItem_child updatedItem = cartItemRepository.save(cartItem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String cartItemId) {
        try {
            Long id = Long.parseLong(cartItemId); // Convert string to Long
            cartItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid cartItemId: " + cartItemId);
        }
    }
}