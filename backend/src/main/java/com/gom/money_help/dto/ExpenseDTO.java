package com.gom.money_help.dto;

import com.gom.money_help.model.Categories;
import com.gom.money_help.model.Expense;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ExpenseDTO {

    private Long id;
    private String name;
    private Categories category;
    private BigDecimal amount;
    private LocalDateTime date;

    public static ExpenseDTO from(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setName(expense.getName());
        dto.setCategory(expense.getCategory());
        dto.setAmount(BigDecimal.valueOf(expense.getAmountInCents()).movePointLeft(2));
        dto.setDate(expense.getDate());
        return dto;
    }
}
