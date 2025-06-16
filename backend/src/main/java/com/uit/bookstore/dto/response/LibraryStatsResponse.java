package com.uit.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibraryStatsResponse {
    long totalBooks;
    long totalUsers;
    long totalBorrowRecords;
    long activeBorrows;
    long overdueBooks;
    long availableBooks;
    long borrowedBooks;
}
