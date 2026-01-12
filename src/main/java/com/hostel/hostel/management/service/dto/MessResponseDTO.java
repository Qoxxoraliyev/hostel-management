package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;
import java.util.List;

public record MessResponseDTO(
   Long messId,
   BigDecimal monthlyExpenses,
   String messTiming,
   List<MessEmployeeResponseDTO> messEmployee
) {}
