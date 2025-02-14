package cart.example.cart.repository;


import cart.example.cart.model.Cart_child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.utils.model.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Optional<Cart> findByUserId(@Param("userId") Long userId);
}