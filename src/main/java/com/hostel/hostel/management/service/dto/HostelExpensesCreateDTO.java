package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;
import java.util.Date;

public record HostelExpensesCreateDTO(
        String description,
        BigDecimal amount,
        Date expenseDate
){}
