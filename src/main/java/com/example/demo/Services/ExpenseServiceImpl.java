package com.example.demo.Services;

import com.example.demo.DTO.Expenserequest;
import com.example.demo.Entities.Expense;
import com.example.demo.Entities.user;
import com.example.demo.Repositories.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final userService userService;
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, userService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }


    @Override
    public Expense addExpense(Expenserequest expenserequest) {
       user currentUser = userService.getCurrentUser();
       Expense expense = Expense.builder()
               .category(expenserequest.getCategory())
               .amount(expenserequest.getAmount())
               .date(LocalDate.now())
               .note(expenserequest.getNote())
               .build();
      Expense newExpense = expenseRepository.save(expense);
      return newExpense;
    };

    @Override
    public List<Expense> getAllExpenses() {
        user currentUser = userService.getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUser_Id(currentUser.getId());

        return List.of(expenses.toArray(new Expense[expenses.size()]));
    }

    @Override
    public List<Expense> getMonthlyExpenses(int year, int month) {
        user currentUser = userService.getCurrentUser();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        List<Expense> expenses = expenseRepository.findByUser_IdAndDateBetween(currentUser.getId(), startDate,endDate);
        return List.of(expenses.toArray(new Expense[expenses.size()]));
    }



    @Override
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(userService.getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized");
        }

        expenseRepository.delete(expense);
    }


}
