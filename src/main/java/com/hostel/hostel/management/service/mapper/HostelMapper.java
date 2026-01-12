package com.hostel.hostel.management.service.mapper;


import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.service.dto.HostelCreateDTO;
import com.hostel.hostel.management.service.dto.HostelDetailDTO;
import com.hostel.hostel.management.service.dto.HostelShortDTO;


public class HostelMapper {

    public static Hostel toEntity(HostelCreateDTO dto){
        Hostel h=new Hostel();
        h.setName(dto.name());
        h.setAnnualExpenses(dto.annualExpenses());
        h.setTotalRooms(dto.totalRooms());
        h.setLocation(dto.location());
        return h;
    }

    public static HostelDetailDTO hostelDetailDTO(Hostel h){
        return new HostelDetailDTO(
                h.getHostelId(),
                h.getName(),
                h.getLocation(),
                h.getTotalRooms(),
                h.getAnnualExpenses(),
                h.getFloors().stream().map(FloorMapper::toResponse).toList(),
                h.getMesses().stream().map(MessMapper::toResponseDTO).toList()
        );
    }

    public static HostelShortDTO hostelShortDTO(Hostel h){
        return new HostelShortDTO(
                h.getHostelId(),
                h.getName(),
                h.getLocation()
        );
    }


}
