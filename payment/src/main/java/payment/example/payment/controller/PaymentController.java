package payment.example.payment.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payment.example.payment.model.Payment_child;
import payment.example.payment.repository.PaymentRepository;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<Payment_child> processPayment(@RequestBody Payment_child payment) {
        Payment_child savedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(savedPayment);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment_child> getPayment(@PathVariable Long paymentId) {
        return paymentRepository.findById(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<String> getPaymentsByOrder(@PathVariable Long orderId) {
        // Logic to fetch payments by order
        return ResponseEntity.ok("Payments fetched for order");
    }

    @PostMapping("/refund")
    public ResponseEntity<String> processRefund(@RequestBody Payment_child payment) {
        // Refund logic here
        return ResponseEntity.ok("Refund processed");
    }

    @GetMapping
    public List<Payment_child> listPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> listPaymentsByUser(@PathVariable Long userId) {
        // Logic to fetch payments by user
        return ResponseEntity.ok("Payments fetched for user");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody String paymentId) {
        // Verification logic here
        return ResponseEntity.ok("Payment verified");
    }

    @GetMapping("/failure")
    public ResponseEntity<List<Payment_child>> listFailedTransactions() {
        // Logic to list failed transactions
        return ResponseEntity.ok(paymentRepository.findAll()); // Modify as needed
    }

    @GetMapping("/success")
    public ResponseEntity<List<Payment_child>> listSuccessfulTransactions() {
        // Logic to list successful transactions
        return ResponseEntity.ok(paymentRepository.findAll()); // Modify as needed
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        paymentRepository.deleteById(paymentId);
        return ResponseEntity.noContent().build();
    }
}