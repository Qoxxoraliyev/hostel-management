package com.hostel.hostel.management.service;

import com.hostel.hostel.management.enums.FurnitureStatus;
import com.hostel.hostel.management.service.dto.FurnitureCreateDTO;
import com.hostel.hostel.management.service.dto.FurnitureResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FurnitureService {

    FurnitureResponseDTO create(FurnitureCreateDTO createDTO);

    FurnitureResponseDTO update(Long furnitureId,FurnitureCreateDTO furnitureCreateDTO);

    FurnitureResponseDTO getById(Long furnitureId);

    void delete(Long furnitureId);

    List<FurnitureResponseDTO> getAll(Pageable pageable);

    List<FurnitureResponseDTO> getByHostel(Long hostelId);

    List<FurnitureResponseDTO> getLowStock(Integer threshold);

    List<FurnitureResponseDTO> getByRoom(Long roomId);

    List<FurnitureResponseDTO> getBrokenFurniture(Long roomId, FurnitureStatus status);

}
