package com.project.project.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @Getter
    private String message;
    private boolean isRead;
    @Setter
    @Getter
    private String type; // e.g., EMAIL, SMS

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Setter
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt; // Timestamp for when the notification was sent

    // Getters and Setters
}