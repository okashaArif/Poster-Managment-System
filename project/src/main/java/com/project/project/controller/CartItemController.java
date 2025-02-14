package com.project.project.controller;

import com.project.project.model.CartItem;
import com.project.project.repository.CartItemRepository;
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
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItem cartItem) {
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
            CartItem savedItem = cartItemRepository.save(cartItem);

            // Return a response with 201 Created status, and a location header for the saved resource
            return ResponseEntity.ok(savedItem);

        } catch (Exception e) {
            // Log the exception and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItem>> getItemsInCart(@PathVariable Long cartId) {
        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @RequestBody CartItem cartItem) {
        cartItem.setCartItemId(cartItemId);
        CartItem updatedItem = cartItemRepository.save(cartItem);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.noContent().build();
    }
}