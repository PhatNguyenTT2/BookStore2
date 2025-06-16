package com.uit.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    String id;
    String title;
    String author;
    String isbn;
    String publisher;
    Integer publishYear;
    String category;
    String description;
    BigDecimal price;
    Integer totalCopies;
    Integer availableCopies;
    String imageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
