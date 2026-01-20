package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.PaymentMethod;
import com.hostel.hostel.management.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;


public record PaymentResponseDTO(
        Long paymentId,
        BigDecimal amountPaid,
        LocalDate paymentDate,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus
) {}
