package com.project.project.controller;

import com.project.project.model.User;
import com.project.project.model.Wishlist;
import com.project.project.repository.UserRepository;
import com.project.project.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) {
        System.out.println("Received Wishlist: " + wishlist);
        // Ensure the user is associated with the wishlist
        if (wishlist.getUser() == null || wishlist.getUser().getUserId() == 0) {
            return ResponseEntity.badRequest().build(); // Handle the case where user is not provided
        }

        // Retrieve the user from the database to ensure it exists
        User user = userRepository.findById(wishlist.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the user in the wishlist
        wishlist.setUser(user);

        // Save the wishlist
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return ResponseEntity.ok(savedWishlist);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable Long userId) {
        return wishlistRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Wishlist> listWishlists() {
        return wishlistRepository.findAll();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Long userId, @RequestBody Wishlist wishlist) {
        wishlist.setWishlistId(userId);
        Wishlist updatedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(updatedWishlist);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long userId) {
        wishlistRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}