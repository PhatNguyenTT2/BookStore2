package com.uit.bookstore.service;

import com.uit.bookstore.dto.request.BookCreationRequest;
import com.uit.bookstore.dto.request.BookUpdateRequest;
import com.uit.bookstore.dto.response.BookResponse;
import com.uit.bookstore.entity.Book;
import com.uit.bookstore.enums.ErrorCode;
import com.uit.bookstore.exception.AppException;
import com.uit.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    public BookResponse createBook(BookCreationRequest request) {
        log.info("Creating new book with title: {}", request.getTitle());

        // Check if book with ISBN already exists
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publisher(request.getPublisher())
                .publishYear(request.getPublishYear())
                .category(request.getCategory())
                .description(request.getDescription())
                .price(request.getPrice())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getTotalCopies()) // Initially all copies are available
                .imageUrl(request.getImageUrl())
                .build();

        book = bookRepository.save(book);
        log.info("Book created successfully with ID: {}", book.getId());

        return mapToBookResponse(book);
    }

    public BookResponse getBookById(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        return mapToBookResponse(book);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toList());
    }

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::mapToBookResponse);
    }

    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toList());
    }

    public List<BookResponse> getBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toList());
    }

    public BookResponse updateBook(String id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        // Check if ISBN is being changed and if new ISBN already exists
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPublisher(request.getPublisher());
        book.setPublishYear(request.getPublishYear());
        book.setCategory(request.getCategory());
        book.setDescription(request.getDescription());
        book.setPrice(request.getPrice());
        
        // Calculate available copies based on the difference
        int difference = request.getTotalCopies() - book.getTotalCopies();
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(book.getAvailableCopies() + difference);
        
        book.setImageUrl(request.getImageUrl());

        book = bookRepository.save(book);
        log.info("Book updated successfully with ID: {}", book.getId());

        return mapToBookResponse(book);
    }

    public void deleteBook(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        bookRepository.delete(book);
        log.info("Book deleted successfully with ID: {}", id);
    }

    public BookResponse borrowBook(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        if (book.getAvailableCopies() <= 0) {
            throw new AppException(ErrorCode.BOOK_NOT_AVAILABLE);
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        book = bookRepository.save(book);

        log.info("Book borrowed successfully. ID: {}, Available copies: {}", 
                book.getId(), book.getAvailableCopies());

        return mapToBookResponse(book);
    }

    public BookResponse returnBook(String id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new AppException(ErrorCode.BOOK_RETURN_INVALID);
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        book = bookRepository.save(book);

        log.info("Book returned successfully. ID: {}, Available copies: {}", 
                book.getId(), book.getAvailableCopies());

        return mapToBookResponse(book);
    }

    private BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .publishYear(book.getPublishYear())
                .category(book.getCategory())
                .description(book.getDescription())
                .price(book.getPrice())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .imageUrl(book.getImageUrl())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
