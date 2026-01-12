package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;

public record HostelCreateDTO(
        String name,
        String location,
        Integer totalRooms,
        BigDecimal annualExpenses
) {}
