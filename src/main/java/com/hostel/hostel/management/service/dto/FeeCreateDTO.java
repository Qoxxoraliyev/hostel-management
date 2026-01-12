package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;

public record FeeCreateDTO(
        BigDecimal month,
        String feeType
) {}
