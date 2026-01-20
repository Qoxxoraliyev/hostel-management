package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.FeeStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record FeeResponseDTO(
        Long feeId,
        BigDecimal month,
        FeeStatus status,
        LocalDate dueDate,
        List<PaymentResponseDTO> payments
) {}
