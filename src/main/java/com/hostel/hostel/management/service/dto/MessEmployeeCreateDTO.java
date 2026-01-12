package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;

public record MessEmployeeCreateDTO(
        String fullName,
        String phone,
        BigDecimal salary,
        String address
) {}
