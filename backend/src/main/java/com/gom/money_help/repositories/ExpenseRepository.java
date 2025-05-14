package com.gom.money_help.repositories;

import com.gom.money_help.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByUserIdOrderByIdDesc(Long userId);

}
