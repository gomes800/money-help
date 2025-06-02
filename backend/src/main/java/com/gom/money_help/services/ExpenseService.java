package com.gom.money_help.services;

import com.gom.money_help.dto.ExpenseDTO;
import com.gom.money_help.dto.PagedResponseDTO;
import com.gom.money_help.model.Expense;
import com.gom.money_help.model.User;
import com.gom.money_help.repositories.ExpenseRepository;
import com.gom.money_help.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public PagedResponseDTO<ExpenseDTO> getAuthenticatedUserExpense(int page, int size) {
        Long userId = authenticatedUserService.getUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Expense> expensePage = expenseRepository.findAllByUserId(userId, pageable);
        Page<ExpenseDTO> dtoPage= expensePage.map(ExpenseDTO::from);

        return new PagedResponseDTO<>(dtoPage);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Transactional
    public Expense addExpenseToUser(ExpenseDTO expenseDTO) {
        Long userId = authenticatedUserService.getUserId();
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

        userService.adjustBalance(userId, expense.getAmountInCents());

        return expenseRepository.save(expense);
    }

    @Transactional
    public Expense updateExpense(Long expenseId, ExpenseDTO updatedDTO) {
        Long userId = authenticatedUserService.getUserId();

        Expense existing = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found."));

        if (!existing.getUser().getId().equals(userId)) {
            throw new SecurityException("Cannot update another user's expense.");
        }

        Long previousAmountInCents = existing.getAmountInCents();

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

        Long newAmountInCents = existing.getAmountInCents();
        long difference = newAmountInCents - previousAmountInCents;

        userService.adjustBalance(userId, difference);

        return expenseRepository.save(existing);
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        Long userId = authenticatedUserService.getUserId();

        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        if (!expense.getUser().getId().equals(userId)) {
            throw new SecurityException("Expense does not belong to user");
        }

        userService.adjustBalance(userId, -expense.getAmountInCents());

        expenseRepository.delete(expense);
    }
}
