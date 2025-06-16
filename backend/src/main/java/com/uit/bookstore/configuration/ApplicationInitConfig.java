package com.uit.bookstore.configuration;

import com.uit.bookstore.entity.User;
import com.uit.bookstore.enums.Role;
import com.uit.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                log.info("Creating default admin user...");
                
                Set<Role> roles = new HashSet<>();
                roles.add(Role.ADMIN);
                roles.add(Role.USER);
                
                User adminUser = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .firstName("Admin")
                        .lastName("User")
                        .email("admin@bookstore.com")
                        .phone("1234567890")
                        .dob(LocalDate.of(1990, 1, 1))
                        .roles(roles)
                        .build();

                userRepository.save(adminUser);
                log.info("Admin user created successfully with username: admin, password: admin123");
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                log.info("Creating default regular user...");
                
                Set<Role> roles = new HashSet<>();
                roles.add(Role.USER);
                
                User regularUser = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user1234"))
                        .firstName("Regular")
                        .lastName("User")
                        .email("user@bookstore.com")
                        .phone("0987654321")
                        .dob(LocalDate.of(1995, 5, 15))
                        .roles(roles)
                        .build();

                userRepository.save(regularUser);
                log.info("Regular user created successfully with username: user, password: user1234");
            }
        };
    }
}
