package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Fee;
import com.hostel.hostel.management.exceptions.FeeNotFoundException;
import com.hostel.hostel.management.repository.FeeRepository;
import com.hostel.hostel.management.service.FeeService;
import com.hostel.hostel.management.service.dto.FeeCreateDTO;
import com.hostel.hostel.management.service.dto.FeeResponseDTO;
import com.hostel.hostel.management.service.mapper.FeeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;

    public FeeServiceImpl(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    @Override
    public FeeResponseDTO create(FeeCreateDTO feeCreateDTO){
        Fee fee= FeeMapper.toEntity(feeCreateDTO);
        Fee savedFee=feeRepository.save(fee);
        return FeeMapper.toResponse(savedFee);
    }



    @Override
    public FeeResponseDTO update(Long feeId,FeeCreateDTO feeCreateDTO){
        Fee fee=feeRepository.findById(feeId).orElseThrow(()->new FeeNotFoundException(
                "Fee not found with id: "+feeId
        ));
        fee.setFeeType(feeCreateDTO.feeType());
        fee.setMonth(feeCreateDTO.month());
        Fee updatedFee=feeRepository.save(fee);
        return FeeMapper.toResponse(updatedFee);
    }


    @Override
    public FeeResponseDTO getById(Long feeId){
        Fee fee=feeRepository.findById(feeId).orElseThrow(()->new FeeNotFoundException(
                "Fee not found with id: "+feeId
        ));
        return FeeMapper.toResponse(fee);
    }


    @Override
    public void delete(Long feeId){
        Fee fee=feeRepository.findById(feeId).orElseThrow(()->new FeeNotFoundException(
                "Fee not found with id: "+feeId
        ));
        feeRepository.delete(fee);
    }


    @Override
    public List<FeeResponseDTO> getAll(Pageable pageable){
        return feeRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(FeeMapper::toResponse)
                .collect(Collectors.toList());
    }



    @Override
    public List<FeeResponseDTO> getByFeeType(String feeType){
        return feeRepository.findByFeeTypeIgnoreCase(feeType)
                .stream()
                .map(FeeMapper::toResponse)
                .collect(Collectors.toList());
    }


}
