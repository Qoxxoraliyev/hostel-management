package com.hostel.hostel.management.service.dto;

import java.util.List;

public record RoomResponseDTO(
        Long roomId,
        Integer roomNumber,
        Integer capacity,
        List<StudentResponseDTO> students,
        List<FurnitureResponseDTO> furnitures
) {}
