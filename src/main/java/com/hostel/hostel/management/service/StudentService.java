package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.StudentCreateDTO;
import com.hostel.hostel.management.service.dto.StudentResponseDTO;

public interface StudentService {

    StudentResponseDTO create(StudentCreateDTO studentCreateDTO);

    StudentResponseDTO update(Long studentId,StudentCreateDTO studentCreateDTO);

    StudentResponseDTO getById(Long studentId);

    void delete(Long studentId);

}
