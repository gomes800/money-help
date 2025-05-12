package com.gom.money_help.dto;

import com.gom.money_help.model.Expense;
import com.gom.money_help.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Data
public class SummaryDTO {

    private BigDecimal balance;
    private List<ExpenseDTO> expenses;

    public static SummaryDTO from(User user) {
        SummaryDTO dto= new SummaryDTO();

        dto.setBalance(BigDecimal.valueOf(user.getBalanceInCents()).movePointLeft(2));

        List<ExpenseDTO> lastExpenses = user.getExpenses().stream()
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                .limit(3)
                .map(ExpenseDTO::from)
                .toList();

        dto.setExpenses(lastExpenses);

        return dto;
    }
}
