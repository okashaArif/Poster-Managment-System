package user.example.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.utils.model.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findSavedCartsByUserId(Long userId);

    Optional<Object> findByUserId(Long userId);
    // Custom query methods can be defined here if needed
}