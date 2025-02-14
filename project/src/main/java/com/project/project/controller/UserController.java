package com.project.project.controller;

import com.project.project.model.Cart;
import com.project.project.model.Order;
import com.project.project.model.User;
import com.project.project.model.Wishlist;
import com.project.project.repository.CartRepository;
import com.project.project.repository.OrderRepository;
import com.project.project.repository.UserRepository;
import com.project.project.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        Cart cart = new Cart();
        Wishlist wishlist = new Wishlist();
        Order order = new Order();
        cart.setUser(user);
        wishlist.setUser(user);
        order.setUser(user);
        user.setCart(cart);
        user.setWishlist(List.of(wishlist)); // Assuming `wishlist` is a List in `User`
        user.setOrders(List.of(order));
        // Save the user (which will automatically save the cart due to CascadeType.ALL)
        userRepository.save(user);
        // Save the cart (optional as it's automatically saved with the user)
        cartRepository.save(cart);
        wishlistRepository.save(wishlist);
        orderRepository.save(order);
        // Return the created user with associated cart
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Password matches, return user details
            return ResponseEntity.ok(user);
        } else {
            // Invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Logout logic here
        return ResponseEntity.ok("Logout successful");
    }



    @GetMapping("/{id}/orders")
    public ResponseEntity<String> getUserOrders(@PathVariable Long id) {
        // Fetch user orders logic here
        return ResponseEntity.ok("User orders fetched");
    }

    @GetMapping("/{id}/wishlist")
    public ResponseEntity<String> getUserWishlist(@PathVariable Long id) {
        // Fetch user wishlist logic here
        return ResponseEntity.ok("User wishlist fetched");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        // Find the user by email
        User user = userRepository.findByEmail(email); // Ensure this method exists in your UserRepository
        if (user != null) {
            user.setPassword(newPassword); // You might want to hash the password before saving
            userRepository.save(user);
            return ResponseEntity.ok("Password reset successful");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
}