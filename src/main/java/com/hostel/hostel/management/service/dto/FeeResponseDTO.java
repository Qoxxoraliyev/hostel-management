package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record FeeResponseDTO(
        Long feeId,
        BigDecimal month,
        String feeType,
        List<PaymentResponseDTO> payments
) {}
