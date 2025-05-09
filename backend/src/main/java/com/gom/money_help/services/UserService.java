package com.gom.money_help.services;

import com.gom.money_help.model.User;
import com.gom.money_help.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User insert(User obj) {
        return repository.save(obj);
    }

    public User update(Long id, User user) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        existing.setName(user.getName());

        return repository.save(existing);
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
    }

    public void addBalance(Long id, double value) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be higher than zero.");
        }
        user.setBalanceInCents(user.getBalanceInCents() + Math.round(value * 100));
        repository.save(user);
    }
}
