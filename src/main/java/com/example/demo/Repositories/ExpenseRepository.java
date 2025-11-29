package com.example.demo.Repositories;

import com.example.demo.Entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    List<Expense> findByUserId(Long userId);
}
