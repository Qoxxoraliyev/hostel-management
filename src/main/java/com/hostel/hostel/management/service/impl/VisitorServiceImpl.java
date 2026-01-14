package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Visitor;
import com.hostel.hostel.management.exceptions.VisitorNotFoundException;
import com.hostel.hostel.management.repository.VisitorRepository;
import com.hostel.hostel.management.service.VisitorService;
import com.hostel.hostel.management.service.dto.VisitorCreateDTO;
import com.hostel.hostel.management.service.dto.VisitorResponseDTO;
import com.hostel.hostel.management.service.mapper.VisitorMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        Visitor visitor=visitorRepository.findById(visitorId).orElseThrow(()->new VisitorNotFoundException("Visitor not found with id: "+visitorId));
        visitor.setVisitDate(visitorCreateDTO.visitDate());
        visitor.setTimeOut(visitorCreateDTO.timeOut());
        visitor.setName(visitorCreateDTO.name());
        visitor.setTimeIn(visitorCreateDTO.timeIn());
        Visitor saved=visitorRepository.save(visitor);
        return VisitorMapper.toResponse(saved);
    }



    @Override
    public VisitorResponseDTO getById(Long visitorId){
        Visitor visitor=visitorRepository.findById(visitorId).orElseThrow(()->new VisitorNotFoundException("Visitor not found with id: "+visitorId));
        return VisitorMapper.toResponse(visitor);
    }


    @Override
    public void delete(Long visitorId){
        Visitor visitor=visitorRepository.findById(visitorId).orElseThrow(()->new VisitorNotFoundException("Visitor not found with id: "+visitorId));
        visitorRepository.delete(visitor);
    }



    @Override
    public List<VisitorResponseDTO> getAll(Pageable pageable){
        return visitorRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(VisitorMapper::toResponse)
                .collect(Collectors.toList());
    }




}
