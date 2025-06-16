package com.uit.bookstore.controller;

import com.uit.bookstore.dto.ApiResponse;
import com.uit.bookstore.dto.request.BorrowRequest;
import com.uit.bookstore.dto.request.ReturnRequest;
import com.uit.bookstore.dto.response.BorrowRecordResponse;
import com.uit.bookstore.service.BorrowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping("/borrow")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<BorrowRecordResponse> borrowBook(@RequestBody BorrowRequest request) {
        return ApiResponse.<BorrowRecordResponse>builder()
                .result(borrowService.borrowBook(request))
                .build();
    }

    @PostMapping("/return")
    @PreAuthorize("hasRole('USER') or hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<BorrowRecordResponse> returnBook(@RequestBody ReturnRequest request) {
        return ApiResponse.<BorrowRecordResponse>builder()
                .result(borrowService.returnBook(request))
                .build();
    }

    @GetMapping("/borrow/user/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN') or #userId == authentication.principal.id")
    public ApiResponse<List<BorrowRecordResponse>> getUserBorrowHistory(@PathVariable String userId) {
        return ApiResponse.<List<BorrowRecordResponse>>builder()
                .result(borrowService.getUserBorrowHistory(userId))
                .build();
    }

    @GetMapping("/borrow/book/{bookId}")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<List<BorrowRecordResponse>> getBookBorrowHistory(@PathVariable String bookId) {
        return ApiResponse.<List<BorrowRecordResponse>>builder()
                .result(borrowService.getBookBorrowHistory(bookId))
                .build();
    }

    @GetMapping("/borrow/all")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<List<BorrowRecordResponse>> getAllBorrowRecords() {
        return ApiResponse.<List<BorrowRecordResponse>>builder()
                .result(borrowService.getAllBorrowRecords())
                .build();
    }

    @GetMapping("/borrow/overdue")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<List<BorrowRecordResponse>> getOverdueRecords() {
        return ApiResponse.<List<BorrowRecordResponse>>builder()
                .result(borrowService.getOverdueRecords())
                .build();
    }

    @GetMapping("/borrow/reports")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<List<BorrowRecordResponse>> getBorrowReports(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ApiResponse.<List<BorrowRecordResponse>>builder()
                .result(borrowService.getRecordsByDateRange(startDate, endDate))
                .build();
    }
}
