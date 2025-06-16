package com.uit.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false)
    String type; // IMPORT, EXPORT

    @Column(nullable = false)
    String status; // PENDING, COMPLETED, CANCELLED

    String description;
    String createdBy;
    String branchId;
    
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime completedAt;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ReceiptItem> items;

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
