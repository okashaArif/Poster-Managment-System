package payment.example.payment.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.example.payment.model.Payment_child;

@Repository
public interface PaymentRepository extends JpaRepository<Payment_child, Long> {
    // Custom query methods can be defined here if needed
}