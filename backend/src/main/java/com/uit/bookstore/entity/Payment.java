package com.uit.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String type; // FINE, DEPOSIT, MEMBERSHIP

    @Column(nullable = false)
    BigDecimal amount;

    @Column(nullable = false)
    String status; // PENDING, COMPLETED, CANCELLED, REFUNDED

    String description;
    String userId;
    String transactionId;
    String paymentMethod; // CASH, CARD, ONLINE
    
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
