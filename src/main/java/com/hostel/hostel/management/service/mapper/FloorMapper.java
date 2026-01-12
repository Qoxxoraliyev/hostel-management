package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.service.dto.FloorCreateDTO;
import com.hostel.hostel.management.service.dto.FloorResponseDTO;
import com.hostel.hostel.management.service.dto.RoomResponseDTO;

public class FloorMapper {

    public static Floor toEntity(FloorCreateDTO dto){
        Floor f=new Floor();
        f.setFloorNumber(dto.floorNumber());
        return f;
    }

    public static FloorResponseDTO toResponse(Floor f){
        return new FloorResponseDTO(
                f.getFloorId(),
                f.getFloorNumber(),
                f.getRooms().stream().map(RoomMapper::toResponse).toList()
        );
    }


}
