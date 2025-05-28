package com.gom.money_help.controllers;

import com.gom.money_help.dto.BalanceRequest;
import com.gom.money_help.dto.SummaryDTO;
import com.gom.money_help.model.User;
import com.gom.money_help.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updated = userService.update(user);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addBalance")
    public ResponseEntity<Void> addBalance(@RequestBody BalanceRequest request) {
        userService.addBalance(request.getValue());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryDTO> summary() {
        SummaryDTO summary = userService.summary();
        return ResponseEntity.ok(summary);
    }
}
