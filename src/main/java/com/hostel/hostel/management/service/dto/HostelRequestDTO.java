package com.hostel.hostel.management.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record HostelRequestDTO(
        @NotBlank String name,
        @NotBlank String location,
        @NotNull Integer totalRooms,
        @NotNull BigDecimal annualExpenses
) {}
