package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.StudentCreateDTO;
import com.hostel.hostel.management.service.dto.StudentResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    StudentResponseDTO create(StudentCreateDTO studentCreateDTO);

    StudentResponseDTO update(Long studentId,StudentCreateDTO studentCreateDTO);

    StudentResponseDTO getById(Long studentId);

    void delete(Long studentId);

    List<StudentResponseDTO> getAll(Pageable pageable);
}
