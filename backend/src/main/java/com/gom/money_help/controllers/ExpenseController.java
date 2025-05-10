package com.gom.money_help.controllers;

import com.gom.money_help.model.Expense;
import com.gom.money_help.services.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> findAll() {
        List<Expense> expenses = expenseService.findAll();
        if (expenses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(Long id) {
        Optional<Expense> expense = expenseService.findById(id);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insert/{userId}")
    public ResponseEntity<Expense> addExpenseToUser(@PathVariable Long userId, @RequestBody Expense expense) {
        Expense newExpense = expenseService.addExpenseToUser(userId, expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @PutMapping("/{userId}/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long userId, @PathVariable Long expenseId, @RequestBody Expense updatedExpense) {
        try {
            Expense updated = expenseService.update(userId, expenseId, updatedExpense);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long expenseId) {
        expenseService.deleteExpense(userId, expenseId);
        return ResponseEntity.noContent().build();
    }
}
