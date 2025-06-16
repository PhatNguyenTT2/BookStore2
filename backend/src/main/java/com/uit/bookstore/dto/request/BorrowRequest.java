package com.uit.bookstore.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowRequest {
    String userId;
    String bookId;
    LocalDate dueDate;
    String notes;
}
