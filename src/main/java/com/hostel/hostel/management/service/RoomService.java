package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.RoomCreateDTO;
import com.hostel.hostel.management.service.dto.RoomResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    RoomResponseDTO create(RoomCreateDTO roomCreateDTO);

    RoomResponseDTO update(Long RoomId,RoomCreateDTO roomCreateDTO);

    RoomResponseDTO getById(Long roomId);

    void delete(Long roomId);

    List<RoomResponseDTO> getAll(Pageable pageable);

}
