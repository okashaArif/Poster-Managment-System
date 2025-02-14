package com.project.project.controller;

import com.project.project.model.Notification;
import com.project.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/email")
    public ResponseEntity<String> sendEmailNotification(@RequestBody Notification notification) {
        // Send email logic here
        return ResponseEntity.ok("Email notification sent");
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sendSmsNotification(@RequestBody Notification notification) {
        // Send SMS logic here
        return ResponseEntity.ok("SMS notification sent");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        // Fetch user notifications logic here
        return ResponseEntity.ok(notificationRepository.findAll()); // Modify as needed
    }

    @GetMapping
    public List<Notification> listNotifications() {
        return notificationRepository.findAll();
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<String> notifyOrderUpdate(@PathVariable Long orderId) {
        // Notify user about order logic here
        return ResponseEntity.ok("User notified about order");
    }

    @PostMapping("/payment/{paymentId}")
    public ResponseEntity<String> notifyPaymentStatus(@PathVariable Long paymentId) {
        // Notify user about payment status logic here
        return ResponseEntity.ok("User notified about payment status");
    }

    @PostMapping("/inventory")
    public ResponseEntity<String> notifyLowStock() {
        // Notify admin about low stock logic here
        return ResponseEntity.ok("Admin notified about low stock");
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationRepository.deleteById(notificationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/promotion")
    public ResponseEntity<String> sendPromotionalNotification(@RequestBody Notification notification) {
        // Send promotional notification logic here
        return ResponseEntity.ok("Promotional notification sent");
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> fetchUnreadNotifications() {
        // Fetch unread notifications logic here
        return ResponseEntity.ok(notificationRepository.findAll()); // Modify as needed
    }
}