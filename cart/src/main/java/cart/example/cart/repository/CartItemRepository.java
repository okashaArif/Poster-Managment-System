package cart.example.cart.repository;

import cart.example.cart.model.CartItem_child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem_child, Long> {
    CartItem_child findByCartIdAndProductId(Long cartId, Long productId);

    List<CartItem_child> findByCartId(Long cartId);
    // Custom query methods can be defined here if needed
}