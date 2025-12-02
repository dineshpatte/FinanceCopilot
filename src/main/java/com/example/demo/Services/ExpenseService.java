package com.example.demo.Services;

import com.example.demo.DTO.Expenserequest;
import com.example.demo.Entities.Expense;

import java.util.List;

public interface ExpenseService {



    Expense addExpense(Expenserequest expenserequest);

  List<Expense> getAllExpenses(Long id);

  List<Expense> getMonthlyExpenses(int year, int month);




    void deleteExpense(Long id);
}
