package com.example.demo.Services;

import com.example.demo.DTO.Expenserequest;
import com.example.demo.Entities.Expense;
import com.example.demo.Entities.user;
import com.example.demo.Repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
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
               .user(currentUser)

               .build();
      Expense newExpense = expenseRepository.save(expense);
      return newExpense;
    };

    @Override
    public List<Expense> getAllExpenses() {
        user currentUser = userService.getCurrentUser();
      return expenseRepository.findByUser_Id(currentUser.getId());
    }

    @Override
    public List<Expense> getMonthlyExpenses(int year, int month) {
        System.out.println("\n=== Getting Monthly Expenses ===");

        try {
            user currentUser = userService.getCurrentUser();
            System.out.println("Current user ID: " + currentUser.getId());
            System.out.println("Current user email: " + currentUser.getEmail());

            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = startDate.plusMonths(1);

            System.out.println("Searching expenses from: " + startDate + " to: " + endDate);

            List<Expense> expenses = expenseRepository.findByUser_IdAndDateBetween(
                    currentUser.getId(),
                    startDate,
                    endDate
            );

            System.out.println("Found " + expenses.size() + " expenses");
            System.out.println("=== End Getting Monthly Expenses ===\n");

            return expenses;
        } catch (Exception e) {
            System.out.println("✗ Error in getMonthlyExpenses: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
