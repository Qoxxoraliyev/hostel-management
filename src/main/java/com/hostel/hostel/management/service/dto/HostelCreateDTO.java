package com.hostel.hostel.management.service.dto;



public record HostelCreateDTO(
        Boolean active,
        String name,
        String location,
        Integer totalRooms
) {}
