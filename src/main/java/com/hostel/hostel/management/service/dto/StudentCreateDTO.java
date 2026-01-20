package com.hostel.hostel.management.service.dto;



import java.time.LocalDate;



public record StudentCreateDTO(
        String fullName,
        Integer age,
        String phone,
        LocalDate dob
) {}
