package com.uit.bookstore.repository;

import com.uit.bookstore.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    List<Branch> findByNameContainingIgnoreCase(String name);
    List<Branch> findByManager(String manager);
}
