package com.example.demo.Controllers;


import com.example.demo.DTO.Expenserequest;
import com.example.demo.Entities.Expense;
import com.example.demo.Entities.user;
import com.example.demo.Repositories.ExpenseRepository;
import com.example.demo.Services.ExpenseService;
import com.example.demo.Services.agentService;
import com.example.demo.Services.userService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final userService userService;
    private final agentService agentService;

    public ExpenseController(ExpenseService expenseService, userService userService, agentService agentService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.agentService = agentService;
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

        return ResponseEntity.ok(expenseService.getAllExpenses());

    }

    @GetMapping("/monthly-expenses")
    public ResponseEntity<?> getMonthlyExpenses(@RequestParam int year, @RequestParam int month) {
        System.out.println("\n=== CONTROLLER REACHED ===");
        System.out.println("Year: " + year + ", Month: " + month);

        try {
            List<Expense> expenses = expenseService.getMonthlyExpenses(year, month);
            System.out.println("Success! Returning " + expenses.size() + " expenses");
            return ResponseEntity.ok(expenses);
        } catch (Exception e) {
            System.out.println("ERROR in controller: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);

        return ResponseEntity.ok("Expense has been deleted");

    }

    @GetMapping("/expense-suggestions")
    public ResponseEntity<?> getExpenseSuggestions(@RequestParam int year, @RequestParam int month) {
        try{
            List<Expense> expenses = expenseService.getMonthlyExpenses(year, month);

            if(expenses.isEmpty()){
                return ResponseEntity.ok(Map.of(
                        "message","No expenses found"
                ));
            }

            Map<String,Object> suggestions  = agentService.getExpenseSuggestions(expenses,year,month);

            return ResponseEntity.ok(suggestions);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
