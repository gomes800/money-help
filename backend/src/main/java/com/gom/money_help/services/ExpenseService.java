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
    private ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense insert(Expense obj) {
        return expenseRepository.save(obj);
    }

    public Expense update(Long id, Expense obj) {
        try {
            Expense entity = expenseRepository.getReferenceById(id);
            updateData(entity, obj);
            return expenseRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private void updateData(Expense entity, Expense obj) {
        entity.setName(obj.getName());
        entity.setCategory(obj.getCategory());
        entity.setValue(obj.getValue());
        entity.setDate(obj.getDate());
    }

    @Transactional
    public void deleteExpense(Long id) {
        try {
            expenseRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Expense not found with ID: " + id);
        }
    }
}
