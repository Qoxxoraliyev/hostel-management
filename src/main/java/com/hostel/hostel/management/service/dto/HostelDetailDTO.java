package com.hostel.hostel.management.service.dto;



import java.math.BigDecimal;
import java.util.List;

public record HostelDetailDTO(
        Long hostelId,
        String name,
        Boolean active,
        String location,
        Integer totalRooms,
        List<FloorResponseDTO> floors,
        List<MessResponseDTO> messes
){}
