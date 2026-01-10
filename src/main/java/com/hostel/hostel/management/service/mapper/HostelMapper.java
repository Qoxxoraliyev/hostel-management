package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.service.dto.HostelRequestDTO;
import com.hostel.hostel.management.service.dto.HostelResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class HostelMapper {

    public static Hostel toEntity(HostelRequestDTO dto){
        Hostel h=new Hostel();
        h.setName(dto.name());
        h.setLocation(dto.location());
        h.setAnnualExpenses(dto.annualExpenses());
        h.setTotalRooms(dto.totalRooms());
        return h;
    }


    public static HostelResponseDTO toResponse(Hostel h){
       return new HostelResponseDTO(
               h.getHostelId(),
               h.getName(),
               h.getLocation()
       );
    }


}
