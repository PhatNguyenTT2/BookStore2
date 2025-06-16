package com.uit.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "borrow_record")
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

    @Column(nullable = false)
    LocalDate borrowDate;

    @Column(nullable = false)
    LocalDate dueDate;

    LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    BorrowStatus status;

    String notes;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum BorrowStatus {
        BORROWED,
        RETURNED,
        OVERDUE,
        LOST
    }
}
