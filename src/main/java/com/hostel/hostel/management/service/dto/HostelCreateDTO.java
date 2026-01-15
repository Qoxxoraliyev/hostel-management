package com.hostel.hostel.management.service.dto;

import java.math.BigDecimal;

public record HostelCreateDTO(
        Boolean active,
        String name,
        String location,
        Integer totalRooms
) {}
