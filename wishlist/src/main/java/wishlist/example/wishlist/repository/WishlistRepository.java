package wishlist.example.wishlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.utils.model.Wishlist;
import wishlist.example.wishlist.model.Wishlist_child;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist_child, Long> {
    // Custom query methods can be defined here if needed
    @Query("SELECT c FROM Wishlist c WHERE c.user.id = :userId AND TYPE(c) != Wishlist")
    List<Wishlist_child> findByUserId(@Param("userId") Long userId);




}