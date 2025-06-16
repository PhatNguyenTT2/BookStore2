package com.uit.bookstore.controller;

import com.uit.bookstore.dto.ApiResponse;
import com.uit.bookstore.dto.request.BookCreationRequest;
import com.uit.bookstore.dto.request.BookUpdateRequest;
import com.uit.bookstore.dto.response.BookResponse;
import com.uit.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ApiResponse<BookResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.createBook(request))
                .build();
    }

    @GetMapping("/books/{id}")
    public ApiResponse<BookResponse> getBook(@PathVariable String id) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookById(id))
                .build();
    }

    @GetMapping("/books")
    public ApiResponse<List<BookResponse>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category) {

        if (search != null && !search.trim().isEmpty()) {
            return ApiResponse.<List<BookResponse>>builder()
                    .result(bookService.searchBooks(search.trim()))
                    .build();
        }

        if (category != null && !category.trim().isEmpty()) {
            return ApiResponse.<List<BookResponse>>builder()
                    .result(bookService.getBooksByCategory(category.trim()))
                    .build();
        }

        // For simple list without pagination info
        if (page == 0 && size == 20) {
            return ApiResponse.<List<BookResponse>>builder()
                    .result(bookService.getAllBooks())
                    .build();
        }

        // For paginated results
        Sort sort = sortDirection.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BookResponse> bookPage = bookService.getAllBooks(pageable);

        return ApiResponse.<List<BookResponse>>builder()
                .result(bookPage.getContent())
                .build();
    }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ApiResponse<BookResponse> updateBook(
            @PathVariable String id,
            @RequestBody BookUpdateRequest request) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.updateBook(id, request))
                .build();
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/books/{id}/borrow")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<BookResponse> borrowBook(@PathVariable String id) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.borrowBook(id))
                .build();
    }

    @PostMapping("/books/{id}/return")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<BookResponse> returnBook(@PathVariable String id) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.returnBook(id))
                .build();
    }

    @GetMapping("/books/search")
    public ApiResponse<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        return ApiResponse.<List<BookResponse>>builder()
                .result(bookService.searchBooks(keyword))
                .build();
    }

    @GetMapping("/books/category/{category}")
    public ApiResponse<List<BookResponse>> getBooksByCategory(@PathVariable String category) {
        return ApiResponse.<List<BookResponse>>builder()
                .result(bookService.getBooksByCategory(category))
                .build();
    }
}
