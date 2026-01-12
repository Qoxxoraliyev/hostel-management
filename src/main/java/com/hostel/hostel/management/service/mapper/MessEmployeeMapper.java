package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.MessEmployee;
import com.hostel.hostel.management.service.dto.MessEmployeeCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;

public class MessEmployeeMapper {

    public static MessEmployee toEntity(MessEmployeeCreateDTO dto){
        MessEmployee m=new MessEmployee();
        m.setPhone(dto.phone());
        m.setAddress(dto.address());
        m.setSalary(dto.salary());
        m.setFullName(dto.fullName());
        return m;
    }

    public static MessEmployeeResponseDTO toResponse(MessEmployee m){
        return new MessEmployeeResponseDTO(
                m.getEmployeeId(),
                m.getFullName(),
                m.getPhone(),
                m.getSalary(),
                m.getAddress()
        );
    }



}
