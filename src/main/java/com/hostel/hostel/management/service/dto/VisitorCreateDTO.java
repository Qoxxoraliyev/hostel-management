package com.hostel.hostel.management.service.dto;


import java.sql.Time;
import java.time.LocalDate;

public record VisitorCreateDTO(
        String name,
        Time timeIn,
        Time timeOut,
        LocalDate visitDate
) {}
