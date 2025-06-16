package com.uit.bookstore.service;

import com.uit.bookstore.dto.request.BorrowRequest;
import com.uit.bookstore.dto.request.ReturnRequest;
import com.uit.bookstore.dto.response.BorrowRecordResponse;
import com.uit.bookstore.entity.Book;
import com.uit.bookstore.entity.BorrowRecord;
import com.uit.bookstore.entity.User;
import com.uit.bookstore.enums.ErrorCode;
import com.uit.bookstore.exception.AppException;
import com.uit.bookstore.repository.BookRepository;
import com.uit.bookstore.repository.BorrowRecordRepository;
import com.uit.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private static final int MAX_BORROW_LIMIT = 5; // Maximum books a user can borrow
    private static final int DEFAULT_BORROW_DAYS = 14; // Default borrow period in days

    @Transactional
    public BorrowRecordResponse borrowBook(BorrowRequest request) {
        log.info("Processing borrow request for user: {} and book: {}", request.getUserId(), request.getBookId());

        // Validate user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Validate book exists
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new AppException(ErrorCode.BOOK_NOT_AVAILABLE);
        }

        // Check user's borrow limit
        long activeBorrows = borrowRecordRepository.countActiveRecordsByUserId(user.getId());
        if (activeBorrows >= MAX_BORROW_LIMIT) {
            throw new AppException(ErrorCode.BORROW_LIMIT_EXCEEDED);
        }

        // Check if user already borrowed this book and hasn't returned it
        List<BorrowRecord> activeRecords = borrowRecordRepository.findActiveRecordsByUserId(user.getId());
        boolean alreadyBorrowed = activeRecords.stream()
                .anyMatch(record -> record.getBook().getId().equals(book.getId()));
        
        if (alreadyBorrowed) {
            throw new AppException(ErrorCode.BOOK_ALREADY_BORROWED);
        }

        // Calculate due date
        LocalDate dueDate = request.getDueDate() != null 
            ? request.getDueDate() 
            : LocalDate.now().plusDays(DEFAULT_BORROW_DAYS);

        // Create borrow record
        BorrowRecord borrowRecord = BorrowRecord.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(dueDate)
                .status(BorrowRecord.BorrowStatus.BORROWED)
                .notes(request.getNotes())
                .build();

        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        borrowRecord = borrowRecordRepository.save(borrowRecord);
        log.info("Book borrowed successfully. Record ID: {}", borrowRecord.getId());

        return mapToBorrowRecordResponse(borrowRecord);
    }

    @Transactional
    public BorrowRecordResponse returnBook(ReturnRequest request) {
        log.info("Processing return request for record: {}", request.getBorrowRecordId());

        BorrowRecord borrowRecord = borrowRecordRepository.findById(request.getBorrowRecordId())
                .orElseThrow(() -> new AppException(ErrorCode.BORROW_RECORD_NOT_FOUND));

        if (borrowRecord.getStatus() != BorrowRecord.BorrowStatus.BORROWED) {
            throw new AppException(ErrorCode.BOOK_ALREADY_RETURNED);
        }

        // Update return information
        borrowRecord.setReturnDate(request.getReturnDate() != null ? request.getReturnDate() : LocalDate.now());
        borrowRecord.setStatus(BorrowRecord.BorrowStatus.RETURNED);
        if (request.getNotes() != null) {
            borrowRecord.setNotes(borrowRecord.getNotes() + " | Return: " + request.getNotes());
        }

        // Update book availability
        Book book = borrowRecord.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        borrowRecord = borrowRecordRepository.save(borrowRecord);
        log.info("Book returned successfully. Record ID: {}", borrowRecord.getId());

        return mapToBorrowRecordResponse(borrowRecord);
    }

    public List<BorrowRecordResponse> getUserBorrowHistory(String userId) {
        return borrowRecordRepository.findByUserId(userId)
                .stream()
                .map(this::mapToBorrowRecordResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordResponse> getBookBorrowHistory(String bookId) {
        return borrowRecordRepository.findByBookId(bookId)
                .stream()
                .map(this::mapToBorrowRecordResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordResponse> getAllBorrowRecords() {
        return borrowRecordRepository.findAll()
                .stream()
                .map(this::mapToBorrowRecordResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordResponse> getOverdueRecords() {
        return borrowRecordRepository.findOverdueRecords(LocalDate.now())
                .stream()
                .map(this::mapToBorrowRecordResponse)
                .collect(Collectors.toList());
    }

    public List<BorrowRecordResponse> getRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return borrowRecordRepository.findByBorrowDateBetween(startDate, endDate)
                .stream()
                .map(this::mapToBorrowRecordResponse)
                .collect(Collectors.toList());
    }

    private BorrowRecordResponse mapToBorrowRecordResponse(BorrowRecord record) {
        boolean isOverdue = record.getStatus() == BorrowRecord.BorrowStatus.BORROWED 
                && record.getDueDate().isBefore(LocalDate.now());

        return BorrowRecordResponse.builder()
                .id(record.getId())
                .userId(record.getUser().getId())
                .userName(record.getUser().getFirstName() + " " + record.getUser().getLastName())
                .bookId(record.getBook().getId())
                .bookTitle(record.getBook().getTitle())
                .bookAuthor(record.getBook().getAuthor())
                .borrowDate(record.getBorrowDate())
                .dueDate(record.getDueDate())
                .returnDate(record.getReturnDate())
                .status(record.getStatus())
                .notes(record.getNotes())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .isOverdue(isOverdue)
                .build();
    }
}
