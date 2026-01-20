package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


public record HostelExpensesCreateDTO(
        String description,
        BigDecimal amount,
        LocalDate expenseDate
){}
