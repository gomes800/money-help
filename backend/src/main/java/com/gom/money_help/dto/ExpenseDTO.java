package com.gom.money_help.dto;

import com.gom.money_help.model.Categories;
import com.gom.money_help.model.Expense;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ExpenseDTO {

    private Long id;
    private String name;
    private Categories category;
    private String description;
    private BigDecimal amount;
    private LocalDate date;

    public static ExpenseDTO from(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setName(expense.getName());
        dto.setCategory(expense.getCategory());
        dto.setDescription(expense.getDescription());
        dto.setAmount(BigDecimal.valueOf(expense.getAmountInCents()).movePointLeft(2));
        dto.setDate(expense.getDate());
        return dto;
    }

    public static List<ExpenseDTO> from(List<Expense> expenses) {
        return expenses.stream()
                .map(ExpenseDTO::from)
                .collect(Collectors.toList());
    }
}
