package com.uit.bookstore.repository;

import com.uit.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    boolean existsByIsbn(String isbn);
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    List<Book> findByCategoryIgnoreCase(String category);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    @Query("SELECT DISTINCT b.category FROM Book b WHERE b.category IS NOT NULL")
    List<String> findAllCategories();
    
    @Query("SELECT b FROM Book b WHERE " +
           "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
           "(:category IS NULL OR LOWER(b.category) = LOWER(:category))")
    List<Book> findBooksWithFilters(@Param("title") String title, 
                                   @Param("author") String author, 
                                   @Param("category") String category);
}
