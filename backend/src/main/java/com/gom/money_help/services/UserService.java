package com.gom.money_help.services;

import com.gom.money_help.dto.SummaryDTO;
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
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User insert(User obj) {
        return userRepository.save(obj);
    }

    public User update(Long id, User user) {
        User existing = userRepository.findById(id)
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

    public void addBalance(Long id, double value) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be higher than zero.");
        }
        user.setBalanceInCents(user.getBalanceInCents() + Math.round(value * 100));
        userRepository.save(user);
    }

    public SummaryDTO summary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return SummaryDTO.from(user);
    }
}
