package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.FloorCreateDTO;
import com.hostel.hostel.management.service.dto.FloorResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FloorService {

    FloorResponseDTO create(FloorCreateDTO floorCreateDTO);

    FloorResponseDTO update(Long floorId,FloorCreateDTO floorCreateDTO);

    FloorResponseDTO getById(Long floorId);

    void delete(Long floorId);

    List<FloorResponseDTO> getAll(Pageable pageable);
}
