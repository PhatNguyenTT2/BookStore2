package com.uit.bookstore.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PopularBookResponse {
    String bookId;
    String title;
    String author;
    long borrowCount;
}
