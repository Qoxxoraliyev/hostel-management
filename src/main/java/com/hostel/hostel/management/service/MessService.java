package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.MessCreateDTO;
import com.hostel.hostel.management.service.dto.MessResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessService {

    MessResponseDTO create(MessCreateDTO createDTO);

    MessResponseDTO update(Long messId,MessCreateDTO messCreateDTO);

    MessResponseDTO getById(Long messId);

    void delete(Long messId);

    List<MessResponseDTO> getAll(Pageable pageable);
}
