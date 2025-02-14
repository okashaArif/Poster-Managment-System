package user.example.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.utils.model.Wishlist;

import java.util.List;

@FeignClient(name = ("wishlist"), url = "http://localhost:8082/wishlists")
public interface wishlistInterface {

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) ;


    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable Long userId);

    @GetMapping
    public List<Wishlist> listWishlists();

    @PutMapping("/{userId}")
    public ResponseEntity<Wishlist> updateWishlist(@PathVariable Long userId, @RequestBody Wishlist wishlist);

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long userId) ;


}
