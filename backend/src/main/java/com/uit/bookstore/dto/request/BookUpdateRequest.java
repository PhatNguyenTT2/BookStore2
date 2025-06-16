package com.uit.bookstore.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    String title;
    String author;
    String isbn;
    String publisher;
    Integer publishYear;
    String category;
    String description;
    BigDecimal price;
    Integer totalCopies;
    String imageUrl;
}
