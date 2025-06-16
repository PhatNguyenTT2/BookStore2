package com.uit.bookstore.service;

import com.uit.bookstore.dto.response.LibraryStatsResponse;
import com.uit.bookstore.dto.response.PopularBookResponse;
import com.uit.bookstore.entity.BorrowRecord;
import com.uit.bookstore.repository.BookRepository;
import com.uit.bookstore.repository.BorrowRecordRepository;
import com.uit.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public LibraryStatsResponse getLibraryStats() {
        log.info("Generating library statistics");

        long totalBooks = bookRepository.count();
        long totalUsers = userRepository.count();
        long totalBorrowRecords = borrowRecordRepository.count();
        
        long activeBorrows = borrowRecordRepository.findByStatus(BorrowRecord.BorrowStatus.BORROWED).size();
        long overdueBooks = borrowRecordRepository.findOverdueRecords(LocalDate.now()).size();
        
        // Calculate available and borrowed books
        long availableBooks = bookRepository.findAll().stream()
                .mapToLong(book -> book.getAvailableCopies())
                .sum();
        
        long borrowedBooks = bookRepository.findAll().stream()
                .mapToLong(book -> book.getTotalCopies() - book.getAvailableCopies())
                .sum();

        return LibraryStatsResponse.builder()
                .totalBooks(totalBooks)
                .totalUsers(totalUsers)
                .totalBorrowRecords(totalBorrowRecords)
                .activeBorrows(activeBorrows)
                .overdueBooks(overdueBooks)
                .availableBooks(availableBooks)
                .borrowedBooks(borrowedBooks)
                .build();
    }

    public List<PopularBookResponse> getPopularBooks(int limit) {
        log.info("Getting top {} popular books", limit);

        return borrowRecordRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                    record -> record.getBook(),
                    Collectors.counting()
                ))
                .entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .limit(limit)
                .map(entry -> PopularBookResponse.builder()
                        .bookId(entry.getKey().getId())
                        .title(entry.getKey().getTitle())
                        .author(entry.getKey().getAuthor())
                        .borrowCount(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public List<PopularBookResponse> getMostBorrowedBooks() {
        return getPopularBooks(10);
    }
}
