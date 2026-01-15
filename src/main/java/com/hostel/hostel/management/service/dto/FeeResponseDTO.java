package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.FeeStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record FeeResponseDTO(
        Long feeId,
        BigDecimal month,
        FeeStatus status,
        Date dueDate,
        List<PaymentResponseDTO> payments
) {}
