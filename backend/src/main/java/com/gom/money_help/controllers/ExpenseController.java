package com.gom.money_help.controllers;

import com.gom.money_help.dto.ExpenseDTO;
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
    public ResponseEntity<ExpenseDTO> addExpense(@PathVariable Long userId, @RequestBody ExpenseDTO expenseDTO) {
        Expense savedExpense = expenseService.addExpenseToUser(userId, expenseDTO);

        ExpenseDTO response = ExpenseDTO.from(savedExpense);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId,
            @RequestBody ExpenseDTO updateDTO) {

        Expense updateExpense = expenseService.updateExpense(userId, expenseId, updateDTO);
        return ResponseEntity.ok(ExpenseDTO.from(updateExpense));
    }

    @DeleteMapping("/{userId}/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long expenseId) {
        expenseService.deleteExpense(userId, expenseId);
        return ResponseEntity.noContent().build();
    }
}
