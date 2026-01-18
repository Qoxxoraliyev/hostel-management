package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.FurnitureStatus;

public record FurnitureResponseDTO(
        Long furnitureId,
        String furnitureType,
        Integer quantity,
        FurnitureStatus status
) {}
