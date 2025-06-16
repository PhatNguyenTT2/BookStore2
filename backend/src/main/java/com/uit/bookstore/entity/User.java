package com.uit.bookstore.entity;

import com.uit.bookstore.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;    @Column(unique = true)
    String username;

    String password;
    String firstName;
    String lastName;

    @Column(unique = true)
    String email;
    
    String phone;
    LocalDate dob;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Role> roles;
}
