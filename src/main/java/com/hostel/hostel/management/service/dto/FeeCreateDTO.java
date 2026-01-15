package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.FeeStatus;

import java.math.BigDecimal;
import java.util.Date;

public record FeeCreateDTO(
        BigDecimal month,
        FeeStatus status,
        Date dueDate
        ) {}
