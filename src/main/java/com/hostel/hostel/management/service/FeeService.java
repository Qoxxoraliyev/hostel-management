package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.FeeCreateDTO;
import com.hostel.hostel.management.service.dto.FeeResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeeService {

    FeeResponseDTO create(FeeCreateDTO feeCreateDTO);

    FeeResponseDTO update(Long feeId,FeeCreateDTO feeCreateDTO);

    FeeResponseDTO getById(Long feeId);

    void delete(Long feeId);

    List<FeeResponseDTO> getAll(Pageable pageable);


}
