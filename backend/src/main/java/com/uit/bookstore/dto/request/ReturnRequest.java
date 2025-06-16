package com.uit.bookstore.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReturnRequest {
    String borrowRecordId;
    LocalDate returnDate;
    String notes;
}
