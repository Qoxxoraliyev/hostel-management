package com.hostel.hostel.management.service.dto;



import java.math.BigDecimal;
import java.util.List;

public record HostelDetailDTO(
        Long hostelId,
        String name,
        String location,
        Integer totalRooms,
        BigDecimal annualExpenses,
        List<FloorResponseDTO> floors,
        List<MessDTO> messes
){}
