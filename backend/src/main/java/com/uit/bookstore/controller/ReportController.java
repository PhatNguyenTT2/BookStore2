package com.uit.bookstore.controller;

import com.uit.bookstore.dto.ApiResponse;
import com.uit.bookstore.dto.response.LibraryStatsResponse;
import com.uit.bookstore.dto.response.PopularBookResponse;
import com.uit.bookstore.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports/stats")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<LibraryStatsResponse> getLibraryStats() {
        return ApiResponse.<LibraryStatsResponse>builder()
                .result(reportService.getLibraryStats())
                .build();
    }

    @GetMapping("/reports/popular-books")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<List<PopularBookResponse>> getPopularBooks(
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.<List<PopularBookResponse>>builder()
                .result(reportService.getPopularBooks(limit))
                .build();
    }

    @GetMapping("/reports/most-borrowed")
    @PreAuthorize("hasRole('LIBRARIAN') or hasRole('ADMIN')")
    public ApiResponse<List<PopularBookResponse>> getMostBorrowedBooks() {
        return ApiResponse.<List<PopularBookResponse>>builder()
                .result(reportService.getMostBorrowedBooks())
                .build();
    }
}
