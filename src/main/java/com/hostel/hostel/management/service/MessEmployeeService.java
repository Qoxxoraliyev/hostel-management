package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.MessEmployeeCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessEmployeeService {

    MessEmployeeResponseDTO create(MessEmployeeCreateDTO messEmployeeCreateDTO);

    MessEmployeeResponseDTO update(Long messEmployeeId,MessEmployeeCreateDTO messEmployeeCreateDTO);

    MessEmployeeResponseDTO getById(Long messEmployeeId);

    void delete(Long messEmployeeId);

    List<MessEmployeeResponseDTO> getAll(Pageable pageable);
}
