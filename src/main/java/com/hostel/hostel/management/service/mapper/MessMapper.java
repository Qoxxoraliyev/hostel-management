package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Mess;
import com.hostel.hostel.management.service.dto.MessCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;
import com.hostel.hostel.management.service.dto.MessResponseDTO;

public class MessMapper {

    public static Mess toEntity(MessCreateDTO dto){
        Mess m=new Mess();
        m.setMessTiming(dto.messTiming());
        m.setMonthlyExpenses(dto.monthlyExpenses());
        return m;
    }


    public static MessResponseDTO toResponseDTO(Mess m){
        return new MessResponseDTO(
                m.getMessId(),
                m.getMonthlyExpenses(),
                m.getMessTiming(),
                m.getMessEmployees().stream().map(MessEmployeeMapper::toResponse).toList()
        );
    }



}
