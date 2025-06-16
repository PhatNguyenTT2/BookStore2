package com.uit.bookstore.repository;

import com.uit.bookstore.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, String> {
    List<BorrowRecord> findByUserId(String userId);
    List<BorrowRecord> findByBookId(String bookId);
    List<BorrowRecord> findByStatus(BorrowRecord.BorrowStatus status);
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.dueDate < :today AND br.status = 'BORROWED'")
    List<BorrowRecord> findOverdueRecords(@Param("today") LocalDate today);
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.user.id = :userId AND br.status = 'BORROWED'")
    List<BorrowRecord> findActiveRecordsByUserId(@Param("userId") String userId);
    
    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.user.id = :userId AND br.status = 'BORROWED'")
    long countActiveRecordsByUserId(@Param("userId") String userId);
    
    @Query("SELECT br FROM BorrowRecord br WHERE br.borrowDate BETWEEN :startDate AND :endDate")
    List<BorrowRecord> findByBorrowDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
