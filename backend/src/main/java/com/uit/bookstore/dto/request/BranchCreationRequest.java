package com.uit.bookstore.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchCreationRequest {
    String name;
    String address;
    String phone;
    String email;
    String manager;
}
