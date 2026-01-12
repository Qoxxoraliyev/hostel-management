package com.hostel.hostel.management.service.dto;



import java.util.Date;


public record StudentCreateDTO(
        String fullName,
        Integer age,
        String phone,
        Date dob
) {}
