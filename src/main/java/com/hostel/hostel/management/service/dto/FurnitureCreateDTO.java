package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.FurnitureStatus;

public record FurnitureCreateDTO(
        String furnitureType,
        Integer quantity,
        FurnitureStatus status
) {}
