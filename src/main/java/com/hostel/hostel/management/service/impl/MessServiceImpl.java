package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Mess;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.MessRepository;
import com.hostel.hostel.management.service.MessService;
import com.hostel.hostel.management.service.dto.MessCreateDTO;
import com.hostel.hostel.management.service.dto.MessResponseDTO;
import com.hostel.hostel.management.service.mapper.MessMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessServiceImpl implements MessService {

    private final MessRepository messRepository;

    public MessServiceImpl(MessRepository messRepository) {
        this.messRepository = messRepository;
    }

    @Override
    public MessResponseDTO create(MessCreateDTO messCreateDTO){
        Mess mess= MessMapper.toEntity(messCreateDTO);
        Mess saved=messRepository.save(mess);
        return MessMapper.toResponseDTO(saved);
    }

    @Override
    public MessResponseDTO update(Long messId,MessCreateDTO messCreateDTO){
        Mess mess=getMessOrThrow(messId);
        mess.setMessTiming(messCreateDTO.messTiming());
        mess.setMonthlyExpenses(messCreateDTO.monthlyExpenses());
        Mess saved=messRepository.save(mess);
        return MessMapper.toResponseDTO(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public MessResponseDTO getById(Long messId){
        return MessMapper.toResponseDTO(getMessOrThrow(messId));
    }

    @Override
    public void delete(Long messId){
        messRepository.delete(getMessOrThrow(messId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<MessResponseDTO> getAll(Pageable pageable){
        return messRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(MessMapper::toResponseDTO).toList();
    }


    private Mess getMessOrThrow(Long messId){
        return messRepository.findById(messId)
                .orElseThrow(()->new AppException(ErrorCode.MESS_NOT_FOUND,"Mess not found with id: "+messId));
    }

}
