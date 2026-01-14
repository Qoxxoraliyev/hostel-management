package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.MessEmployee;
import com.hostel.hostel.management.exceptions.MessNotFoundException;
import com.hostel.hostel.management.repository.MessEmployeeRepository;
import com.hostel.hostel.management.service.MessEmployeeService;
import com.hostel.hostel.management.service.dto.MessEmployeeCreateDTO;
import com.hostel.hostel.management.service.dto.MessEmployeeResponseDTO;
import com.hostel.hostel.management.service.mapper.MessEmployeeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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



    @Override
    public MessEmployeeResponseDTO update(Long messEmployeeId,MessEmployeeCreateDTO messEmployeeCreateDTO){
        MessEmployee messEmployee=messEmployeeRepository.findById(messEmployeeId).orElseThrow(()->new MessNotFoundException("MessEmployee not found with id: "+messEmployeeId));
        messEmployee.setFullName(messEmployeeCreateDTO.fullName());
        messEmployee.setSalary(messEmployeeCreateDTO.salary());
        messEmployee.setAddress(messEmployeeCreateDTO.address());
        messEmployee.setPhone(messEmployeeCreateDTO.phone());
        MessEmployee saved=messEmployeeRepository.save(messEmployee);
        return MessEmployeeMapper.toResponse(saved);
    }



    @Override
    public MessEmployeeResponseDTO getById(Long messEmployeeId){
        MessEmployee messEmployee=messEmployeeRepository.findById(messEmployeeId).orElseThrow(()->new MessNotFoundException("MessEmployee not found with id: "+messEmployeeId));
        return MessEmployeeMapper.toResponse(messEmployee);
    }


    @Override
    public void delete(Long messEmployeeId){
        MessEmployee messEmployee=messEmployeeRepository.findById(messEmployeeId).orElseThrow(()->new MessNotFoundException("MessEmployee not found with id: "+messEmployeeId));
        messEmployeeRepository.delete(messEmployee);
    }


    @Override
    public List<MessEmployeeResponseDTO> getAll(Pageable pageable){
        return messEmployeeRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(MessEmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }




}
