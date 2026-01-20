package com.hostel.hostel.management.service.dto;

import java.time.LocalDate;
import java.util.List;

public record StudentResponseDTO(
        Long studentId,
        String fullName,
        Integer age,
        String phone,
        LocalDate dob,
        List<FeeResponseDTO> fees,
        List<PaymentResponseDTO> payments,
        List<VisitorResponseDTO> visitors
) {}
