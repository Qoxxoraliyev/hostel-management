package com.hostel.hostel.management.service.mapper;


import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.service.dto.*;

import java.util.List;


public class HostelMapper {

    public static Hostel toEntity(HostelCreateDTO dto){
        Hostel h=new Hostel();
        h.setName(dto.name());
        h.setTotalRooms(dto.totalRooms());
        h.setLocation(dto.location());
        return h;
    }

    public static HostelDetailDTO hostelDetailDTO(Hostel h){
        List<FloorResponseDTO> floors=
                h.getFloors()==null?List.of()
                        :h.getFloors().stream().map(FloorMapper::toResponse).toList();

        List<MessResponseDTO> messes=
                h.getMesses()==null?List.of()
                        :h.getMesses().stream().map(MessMapper::toResponseDTO).toList();

        return new HostelDetailDTO(
                h.getHostelId(),
                h.getName(),
                h.getLocation(),
                h.getTotalRooms(),
                floors,
                messes
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
