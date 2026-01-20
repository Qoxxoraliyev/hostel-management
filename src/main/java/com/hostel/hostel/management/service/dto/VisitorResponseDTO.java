package com.hostel.hostel.management.service.dto;


import java.sql.Time;
import java.time.LocalDate;

public record VisitorResponseDTO(
        Long visitorId,
        String name,
        Time timeIn,
        Time timeOut,
        LocalDate visitDate
) {}
