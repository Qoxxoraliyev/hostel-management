package com.hostel.hostel.management.service.dto;

import java.sql.Date;
import java.sql.Time;

public record VisitorCreateDTO(
        String name,
        Time timeIn,
        Time timeOut,
        Date visitDate
) {}
