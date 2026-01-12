package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Visitor;
import com.hostel.hostel.management.service.dto.VisitorCreateDTO;
import com.hostel.hostel.management.service.dto.VisitorResponseDTO;

public class VisitorMapper {

    public static Visitor toEntity(VisitorCreateDTO dto){
        Visitor v=new Visitor();
        v.setName(dto.name());
        v.setTimeIn(dto.timeIn());
        v.setTimeOut(dto.timeOut());
        v.setVisitDate(dto.visitDate());
        return v;
    }

    public static VisitorResponseDTO toResponse(Visitor v){
        return new VisitorResponseDTO(
                v.getVisitorId(),
                v.getName(),
                v.getTimeIn(),
                v.getTimeOut(),
                v.getVisitDate()
        );
    }


}
