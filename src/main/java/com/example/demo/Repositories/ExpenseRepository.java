package com.example.demo.Repositories;

import com.example.demo.Entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Correct way to fetch expenses for a user
    List<Expense> findByUser_Id(Long userId);

    // Correct way to fetch expenses between two dates
    List<Expense> findByUser_IdAndDateBetween(Long userId, LocalDate start, LocalDate end);
}
