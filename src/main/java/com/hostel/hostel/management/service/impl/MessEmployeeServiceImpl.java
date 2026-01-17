package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.MessEmployee;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.MessEmployeeRepository;
import com.hostel.hostel.management.service.MessEmployeeService;
import com.hostel.hostel.management.service.dto.MessEmployeeCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;
import com.hostel.hostel.management.service.mapper.MessEmployeeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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



    @Override
    public MessEmployeeResponseDTO update(Long messEmployeeId,MessEmployeeCreateDTO messEmployeeCreateDTO){
        MessEmployee messEmployee=getMessEmployeeOrThrow(messEmployeeId);
        messEmployee.setFullName(messEmployeeCreateDTO.fullName());
        messEmployee.setSalary(messEmployeeCreateDTO.salary());
        messEmployee.setAddress(messEmployeeCreateDTO.address());
        messEmployee.setPhone(messEmployeeCreateDTO.phone());
        MessEmployee saved=messEmployeeRepository.save(messEmployee);
        return MessEmployeeMapper.toResponse(saved);
    }



    @Override
    @Transactional(readOnly = true)
    public MessEmployeeResponseDTO getById(Long messEmployeeId){
        return MessEmployeeMapper.toResponse(getMessEmployeeOrThrow(messEmployeeId));
    }


    @Override
    public void delete(Long messEmployeeId){
        messEmployeeRepository.delete(getMessEmployeeOrThrow(messEmployeeId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<MessEmployeeResponseDTO> getAll(Pageable pageable){
        return messEmployeeRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(MessEmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }


    private MessEmployee getMessEmployeeOrThrow(Long messEmployeeId){
        return messEmployeeRepository.findById(messEmployeeId)
                .orElseThrow(()->new AppException(ErrorCode.MESS_EMPLOYEE_NOT_FOUND,"MessEmployee not found with id: "+messEmployeeId));
    }


}
