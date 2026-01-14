package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.VisitorCreateDTO;
import com.hostel.hostel.management.service.dto.VisitorResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VisitorService {

    VisitorResponseDTO create(VisitorCreateDTO visitorCreateDTO);

    VisitorResponseDTO update(Long visitorId,VisitorCreateDTO visitorCreateDTO);

    VisitorResponseDTO getById(Long visitorId);

    void delete(Long visitorId);

    List<VisitorResponseDTO> getAll(Pageable pageable);
}
