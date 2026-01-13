package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.MessEmployee;
import com.hostel.hostel.management.repository.MessEmployeeRepository;
import com.hostel.hostel.management.service.MessEmployeeService;
import com.hostel.hostel.management.service.dto.MessEmployeeCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;
import com.hostel.hostel.management.service.mapper.MessEmployeeMapper;
import org.springframework.stereotype.Service;

@Service
public class MessEmployeeServiceImpl implements MessEmployeeService {

    private final MessEmployeeRepository messEmployeeRepository;

    public MessEmployeeServiceImpl(MessEmployeeRepository messEmployeeRepository) {
        this.messEmployeeRepository = messEmployeeRepository;
    }


    @Override
    public MessEmployeeResponseDTO create(MessEmployeeCreateDTO messEmployeeCreateDTO){
        MessEmployee messEmployee= MessEmployeeMapper.toEntity(messEmployeeCreateDTO);
        MessEmployee saved=messEmployeeRepository.save(messEmployee);
        return MessEmployeeMapper.toResponse(saved);
    }


}
