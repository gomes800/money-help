package com.gom.money_help.services;

import com.gom.money_help.dto.ExpenseDTO;
import com.gom.money_help.model.Expense;
import com.gom.money_help.model.User;
import com.gom.money_help.repositories.ExpenseRepository;
import com.gom.money_help.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ExpenseDTO> getAllUsersExpenses(Long userId, Long requestingUserId) {
        if (!userId.equals(requestingUserId)) {
            throw new SecurityException("Acess denied: user can only access their own expenses.");
        }

        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found.");
        }
        List<Expense> userExpenses = expenseRepository.findAllByUserIdOrderByIdDesc(userId);
        return userExpenses.stream()
                .map(ExpenseDTO::from)
                .collect(Collectors.toList());
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public Expense addExpenseToUser(Long userId, ExpenseDTO expenseDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        if(expenseDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense value must be positive");
        }

        Expense expense = new Expense();
        expense.setUser(user);
        expense.setName(expenseDTO.getName());
        expense.setDescription(expenseDTO.getDescription());
        expense.setCategory(expenseDTO.getCategory());
        expense.setAmountInCents(expenseDTO.getAmount().movePointRight(2).longValueExact());
        expense.setDate(expenseDTO.getDate());

        return expenseRepository.save(expense);
    }

    @Transactional
    public Expense updateExpense(Long userId, Long expenseId, ExpenseDTO updatedDTO) {
        Expense existing = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found."));

        if (!existing.getUser().getId().equals(userId)) {
            throw new SecurityException("Cannot update another user's expense.");
        }

        if (updatedDTO.getName() != null) existing.setName(updatedDTO.getName());
        if (updatedDTO.getCategory() != null) existing.setCategory(updatedDTO.getCategory());
        if (updatedDTO.getDescription() != null) existing.setDescription(updatedDTO.getDescription());
        if (updatedDTO.getAmount() != null) {
            if (updatedDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Expense amount must be positive.");
            }
            existing.setAmountInCents(updatedDTO.getAmount().movePointRight(2).longValueExact());
        }
        if (updatedDTO.getDate() != null) existing.setDate(updatedDTO.getDate());

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
