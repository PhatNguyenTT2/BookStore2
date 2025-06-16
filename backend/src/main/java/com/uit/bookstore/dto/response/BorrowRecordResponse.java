package com.uit.bookstore.dto.response;

import com.uit.bookstore.entity.BorrowRecord;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowRecordResponse {
    String id;
    String userId;
    String userName;
    String bookId;
    String bookTitle;
    String bookAuthor;
    LocalDate borrowDate;
    LocalDate dueDate;
    LocalDate returnDate;
    BorrowRecord.BorrowStatus status;
    String notes;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean isOverdue;
}
