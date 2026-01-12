package com.hostel.hostel.management.service.dto;

import java.util.List;

public record FloorResponseDTO(
        Long floorId,
        Integer floorNumber,
        List<RoomDTO> rooms
) {}
