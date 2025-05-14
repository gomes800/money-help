package com.gom.money_help.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SummaryDTO {

    private BigDecimal balance;
    private List<ExpenseDTO> expenses;
}
