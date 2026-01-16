package com.hostel.hostel.management.service.dto;

import com.hostel.hostel.management.enums.CleaningStatus;

import java.util.List;

public record RoomResponseDTO(
        Long roomId,
        Integer roomNumber,
        Integer capacity,
        CleaningStatus status,
        List<StudentResponseDTO> students,
        List<FurnitureResponseDTO> furnitures
) {}
