package com.hostel.hostel.management.service;

import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.repository.HostelRepository;
import com.hostel.hostel.management.service.dto.HostelRequestDTO;
import com.hostel.hostel.management.service.dto.HostelResponseDTO;
import com.hostel.hostel.management.service.mapper.HostelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HostelService {

    private final HostelRepository hostelRepository;

    public HostelService(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }


    @Transactional
    public HostelResponseDTO save(HostelRequestDTO dto){
        Hostel h= HostelMapper.toEntity(dto);
        return HostelMapper.toResponse(hostelRepository.save(h));
    }


    public List<HostelResponseDTO> searchByName(String name){
        return hostelRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(HostelMapper::toResponse)
                .toList();
    }




}
