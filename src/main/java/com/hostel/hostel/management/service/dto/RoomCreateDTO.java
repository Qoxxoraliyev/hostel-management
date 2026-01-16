package com.hostel.hostel.management.service.dto;


import com.hostel.hostel.management.enums.CleaningStatus;

public record RoomCreateDTO(
        Integer roomNumber,
        Integer capacity,
        CleaningStatus status
        ) {}
