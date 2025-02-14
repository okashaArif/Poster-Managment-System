package wishlist.example.wishlist.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.Cart;
import org.utils.model.User;
import org.utils.model.Wishlist;
import wishlist.example.wishlist.model.Wishlist_child;
import wishlist.example.wishlist.repository.UserRepository;
import wishlist.example.wishlist.repository.WishlistRepository;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/wishlists")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Wishlist_child> createWishlist(@RequestBody Wishlist_child wishlist) {
        System.out.println("Received Wishlist: " + wishlist);
        if (wishlist.getUser() == null || wishlist.getUser().getUserId() == 0) {
            return ResponseEntity.badRequest().build(); // Handle the case where user is not provided
        }

        User user = userRepository.findById(wishlist.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        wishlist.setUser(user);

        Wishlist_child savedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(savedWishlist);
    }

    @GetMapping("/{userId}")
    @Transactional
    public ResponseEntity<Object> getWishlistByUserId(@PathVariable Long userId) {
        System.out.println("Fetching wishlists for userId: " + userId);
        List<Wishlist_child> wishlists = wishlistRepository.findByUserId(userId);

        if (wishlists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No wishlists found for the user.");
        }

        return ResponseEntity.ok(wishlists);
    }

    @GetMapping
    public List<Wishlist_child> listWishlists() {
        return wishlistRepository.findAll();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Wishlist_child> updateWishlist(@PathVariable Long userId, @RequestBody Wishlist_child wishlist) {
        wishlist.setWishlistId(userId);
        Wishlist_child updatedWishlist = wishlistRepository.save(wishlist);
        return ResponseEntity.ok(updatedWishlist);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long userId) {
        wishlistRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        List<Wishlist_child> wishlists = wishlistRepository.findByUserId(userId);

        if (wishlists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Iterate through wishlists and remove the product if found
        for (Wishlist_child wishlist : wishlists) {
            wishlist.getProducts().removeIf(product -> product.getId().equals(productId));
            wishlistRepository.save(wishlist); // Save the updated wishlist
        }

        return ResponseEntity.noContent().build();
    }
}
