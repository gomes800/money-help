package com.gom.money_help.services;

import com.gom.money_help.model.Expense;
import com.gom.money_help.repositories.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Optional<Expense> findById(Long id) {
        return repository.findById(id);
    }

    public Expense insert(Expense obj) {
        return repository.save(obj);
    }

    public Expense update(Long id, Expense expense) {
        Expense existing = repository. findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found."));

        existing.setName(expense.getName());
        existing.setCategory(expense.getCategory());
        existing.setValue(expense.getValue());
        existing.setDate(expense.getDate());

        return repository.save(existing);
    }

    @Transactional
    public void deleteExpense(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Expense not found with ID: " + id);
        }
    }
}
