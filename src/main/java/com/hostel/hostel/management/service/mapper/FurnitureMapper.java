package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Furniture;
import com.hostel.hostel.management.service.dto.FurnitureCreateDTO;
import com.hostel.hostel.management.service.dto.FurnitureResponseDTO;

public class FurnitureMapper {

    public static Furniture toEntity(FurnitureCreateDTO dto){
        Furniture f=new Furniture();
        f.setFurnitureType(dto.furnitureType());
        f.setQuantity(dto.quantity());
        f.setStatus(dto.status());
        return f;
    }


    public static FurnitureResponseDTO toResponse(Furniture f){
        return new FurnitureResponseDTO(
                f.getFurnitureId(),
                f.getFurnitureType(),
                f.getQuantity(),
                f.getStatus()
        );
    }


}
