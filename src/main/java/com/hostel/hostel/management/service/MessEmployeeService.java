package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.MessEmployeeCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;

public interface MessEmployeeService {

    MessEmployeeResponseDTO create(MessEmployeeCreateDTO messEmployeeCreateDTO);

    MessEmployeeResponseDTO update(Long messEmployeeId,MessEmployeeCreateDTO messEmployeeCreateDTO);
}
