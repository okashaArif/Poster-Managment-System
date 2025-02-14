package wishlist.example.wishlist.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.utils.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    User findByUsername(String username);
    User findByEmail(String email);

}