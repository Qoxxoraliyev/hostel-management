package com.hostel.hostel.management.service.dto;

import java.util.Date;
import java.util.List;

public record StudentResponseDTO(
        Long studentId,
        String fullName,
        Integer age,
        String phone,
        Date dob,
        List<FeeResponseDTO> fees,
        List<PaymentResponseDTO> payments,
        List<VisitorResponseDTO> visitors
) {}
