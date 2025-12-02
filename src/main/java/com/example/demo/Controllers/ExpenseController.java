package com.example.demo.Controllers;


import com.example.demo.DTO.Expenserequest;
import com.example.demo.Entities.Expense;
import com.example.demo.Entities.user;
import com.example.demo.Services.ExpenseService;
import com.example.demo.Services.userService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final userService userService;

    public ExpenseController(ExpenseService expenseService, userService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    //create expense

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expenserequest expenserequest) {
        user newUser = userService.getCurrentUser();
        Expense newExpense = expenseService.addExpense(expenserequest);

        return ResponseEntity.ok(newExpense);
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getuserExpenses() {
        user currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(expenseService.getAllExpenses(currentUser.getId()));

    }

    @GetMapping("/monthly-expenses")
    public ResponseEntity<List<Expense>> getmontlyExpenses(@RequestParam int year, @RequestParam int month) {
        user currentUser = userService.getCurrentUser();



        List<Expense> expenses = expenseService.getMonthlyExpenses(year, month);
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);

        return ResponseEntity.ok("Expense has been deleted");

    }
}
