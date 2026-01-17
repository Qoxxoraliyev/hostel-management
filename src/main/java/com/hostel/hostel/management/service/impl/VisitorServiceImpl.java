package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Visitor;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.VisitorRepository;
import com.hostel.hostel.management.service.VisitorService;
import com.hostel.hostel.management.service.dto.VisitorCreateDTO;
import com.hostel.hostel.management.service.dto.VisitorResponseDTO;
import com.hostel.hostel.management.service.mapper.VisitorMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }


    @Override
    public VisitorResponseDTO create(VisitorCreateDTO visitorCreateDTO){
        Visitor visitor= VisitorMapper.toEntity(visitorCreateDTO);
        Visitor saved=visitorRepository.save(visitor);
        return VisitorMapper.toResponse(saved);
    }


    @Override
    public VisitorResponseDTO update(Long visitorId,VisitorCreateDTO visitorCreateDTO){
        Visitor visitor=getVisitorOrThrow(visitorId);
        visitor.setVisitDate(visitorCreateDTO.visitDate());
        visitor.setTimeOut(visitorCreateDTO.timeOut());
        visitor.setName(visitorCreateDTO.name());
        visitor.setTimeIn(visitorCreateDTO.timeIn());
        Visitor saved=visitorRepository.save(visitor);
        return VisitorMapper.toResponse(saved);
    }



    @Override
    @Transactional(readOnly = true)
    public VisitorResponseDTO getById(Long visitorId){
        return VisitorMapper.toResponse(getVisitorOrThrow(visitorId));
    }


    @Override
    public void delete(Long visitorId){
        visitorRepository.delete(getVisitorOrThrow(visitorId));
    }



    @Override
    @Transactional(readOnly = true)
    public List<VisitorResponseDTO> getAll(Pageable pageable){
        return visitorRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(VisitorMapper::toResponse)
                .collect(Collectors.toList());
    }


    private Visitor getVisitorOrThrow(Long visitorId){
        return visitorRepository.findById(visitorId)
                .orElseThrow(()->new AppException(ErrorCode.STUDENT_NOT_FOUND,"Student not found with id: "+visitorId));
    }


}
