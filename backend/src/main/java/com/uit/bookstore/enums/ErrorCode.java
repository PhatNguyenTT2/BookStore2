package com.uit.bookstore.enums;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    INVALID_KEY(1001, "Invalid message key"),
    USER_EXISTED(1002, "User existed"),
    USERNAME_INVALID(1003, "Username must be at least 4 characters"),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters"),    USER_NOT_EXISTED(1005, "User not existed"),
    UNAUTHENTICATED(1006, "Unauthenticated"),
    UNAUTHORIZED(1007, "You do not have permission"),
    INVALID_DOB(1008, "Your age must be at least 18"),
    BOOK_NOT_EXISTED(1009, "Book not existed"),
    INVALID_EMAIL(1012, "Invalid email format"),
    EMAIL_EXISTED(1013, "Email already existed"),
    BOOK_EXISTED(1014, "Book with this ISBN already existed"),
    BOOK_NOT_AVAILABLE(1016, "Book is not available for borrowing"),
    BOOK_RETURN_INVALID(1017, "Invalid book return operation"),
    BORROW_LIMIT_EXCEEDED(1018, "User has reached maximum borrow limit"),
    BOOK_ALREADY_BORROWED(1019, "User has already borrowed this book"),
    BORROW_RECORD_NOT_FOUND(1020, "Borrow record not found"),
    BOOK_ALREADY_RETURNED(1021, "Book has already been returned");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
