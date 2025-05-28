package com.gom.money_help.services;

import com.gom.money_help.dto.ExpenseDTO;
import com.gom.money_help.dto.SummaryDTO;
import com.gom.money_help.model.Expense;
import com.gom.money_help.model.User;
import com.gom.money_help.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(User user) {
        Long userId = authenticatedUserService.getUserId();

        User existing = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        existing.setName(user.getName());

        return userRepository.save(existing);
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
    }

    public void addBalance(double value) {
        Long userId = authenticatedUserService.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be higher than zero.");
        }
        user.setBalanceInCents(user.getBalanceInCents() + Math.round(value * 100));
        userRepository.save(user);
    }

    public SummaryDTO summary() {
        Long userId = authenticatedUserService.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        SummaryDTO dto = new SummaryDTO();
        dto.setBalance(BigDecimal.valueOf(user.getBalanceInCents()).movePointLeft(2));

        List<ExpenseDTO> lastExpenses = user.getExpenses().stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .limit(3)
                .map(ExpenseDTO::from)
                .collect(Collectors.toList());

        dto.setExpenses(lastExpenses);
        return dto;
    }
}
