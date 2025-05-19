package com.gom.money_help.repositories;

import com.gom.money_help.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findAllByUserId(Long userId, Pageable pageable);

}
