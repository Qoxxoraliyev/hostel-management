package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.service.dto.FurnitureResponseDTO;
import com.hostel.hostel.management.service.dto.RoomCreateDTO;
import com.hostel.hostel.management.service.dto.RoomResponseDTO;
import com.hostel.hostel.management.service.dto.StudentResponseDTO;

public class RoomMapper {

    public static Room toEntity(RoomCreateDTO dto){
        Room r=new Room();
        r.setCapacity(dto.capacity());
        r.setRoomNumber(dto.roomNumber());
        return r;
    }

    public static RoomResponseDTO toResponse(Room r){
        return new RoomResponseDTO(
                r.getRoomId(),
                r.getRoomNumber(),
                r.getCapacity(),
                r.getStudents().stream().map(StudentMapper::toResponse).toList(),
                r.getFurnitures().stream().map(FurnitureMapper::toResponse).toList()
        );
    }

}
