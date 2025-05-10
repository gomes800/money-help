package com.gom.money_help.services;

import com.gom.money_help.model.Expense;
import com.gom.money_help.model.User;
import com.gom.money_help.repositories.ExpenseRepository;
import com.gom.money_help.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public Expense addExpenseToUser(Long id, Expense expense) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        if(expense.getValue() <= 0) {
            throw new IllegalArgumentException("Expense value must be positive");
        }

        expense.setUser(user);
        user.getExpenses().add(expense);

        return expenseRepository.save(expense);
    }

    public Expense update(Long userId, Long expenseId, Expense updatedExpense) {
        Expense existing = expenseRepository. findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found."));

        if (!existing.getUser().getId().equals(userId)) {
            throw new SecurityException("Cannot update another user's expense.");
        }

        if (updatedExpense.getName() != null) existing.setName(updatedExpense.getName());
        if (updatedExpense.getCategory() != null) existing.setCategory(updatedExpense.getCategory());
        if (updatedExpense.getValue() != null) existing.setValue(updatedExpense.getValue());
        if (updatedExpense.getDate() != null) existing.setDate(updatedExpense.getDate());

        return expenseRepository.save(existing);
    }

    @Transactional
    public void deleteExpense(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense nott found"));

        if (!expense.getUser().getId().equals(userId)) {
            throw new SecurityException("Expense does not belong to user");
        }

        expenseRepository.delete(expense);
    }
}
