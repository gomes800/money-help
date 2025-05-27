package com.gom.money_help.controllers;

import com.gom.money_help.dto.ExpenseDTO;
import com.gom.money_help.dto.PagedResponseDTO;
import com.gom.money_help.model.Expense;
import com.gom.money_help.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/user")
    public ResponseEntity<PagedResponseDTO<ExpenseDTO>> getUsersExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        PagedResponseDTO<ExpenseDTO> expenses = expenseService.getAuthenticatedUserExpense(page, size);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(Long id) {
        Optional<Expense> expense = expenseService.findById(id);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insert")
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO expenseDTO) {
        Expense savedExpense = expenseService.addExpenseToUser(expenseDTO);

        ExpenseDTO response = ExpenseDTO.from(savedExpense);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable Long expenseId,
            @RequestBody ExpenseDTO updateDTO) {

        Expense updateExpense = expenseService.updateExpense(expenseId, updateDTO);
        return ResponseEntity.ok(ExpenseDTO.from(updateExpense));
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
