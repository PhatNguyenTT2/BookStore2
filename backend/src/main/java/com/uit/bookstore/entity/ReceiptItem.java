package com.uit.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "receipt_item")
public class ReceiptItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    Book book;

    @Column(nullable = false)
    Integer quantity;

    String notes;
}
