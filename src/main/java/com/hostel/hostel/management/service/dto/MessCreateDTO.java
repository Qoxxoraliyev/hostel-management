package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;

public record MessCreateDTO(
        BigDecimal monthlyExpenses,
        String messTiming
) {}
