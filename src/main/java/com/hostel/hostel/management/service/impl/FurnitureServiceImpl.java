package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Furniture;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.enums.FurnitureStatus;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.FurnitureRepository;
import com.hostel.hostel.management.service.FurnitureService;
import com.hostel.hostel.management.service.dto.FurnitureCreateDTO;
import com.hostel.hostel.management.service.dto.FurnitureResponseDTO;
import com.hostel.hostel.management.service.mapper.FurnitureMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FurnitureServiceImpl implements FurnitureService {

    private final FurnitureRepository furnitureRepository;

    public FurnitureServiceImpl(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }


    @Override
    public FurnitureResponseDTO create(FurnitureCreateDTO furnitureCreateDTO) {
        Furniture furniture = FurnitureMapper.toEntity(furnitureCreateDTO);
        Furniture saved = furnitureRepository.save(furniture);
        return FurnitureMapper.toResponse(saved);
    }


    @Override
    public FurnitureResponseDTO update(Long furnitureId, FurnitureCreateDTO furnitureCreateDTO) {
        Furniture furniture = getFurnitureOrThrow(furnitureId);
        furniture.setFurnitureType(furnitureCreateDTO.furnitureType());
        furniture.setQuantity(furnitureCreateDTO.quantity());
        Furniture saved = furnitureRepository.save(furniture);
        return FurnitureMapper.toResponse(saved);
    }


    @Override
    public FurnitureResponseDTO getById(Long furnitureId) {
        return FurnitureMapper.toResponse(getFurnitureOrThrow(furnitureId));
    }


    @Override
    public void delete(Long furnitureId) {
        furnitureRepository.delete(getFurnitureOrThrow(furnitureId));
    }


    @Override
    public List<FurnitureResponseDTO> getAll(Pageable pageable) {
        return furnitureRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(FurnitureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FurnitureResponseDTO> getLowStock(Integer threshold) {
        return furnitureRepository.findByQuantityLessThan(threshold)
                .stream()
                .map(FurnitureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FurnitureResponseDTO> getByHostel(Long hostelId) {
        return furnitureRepository.findAllByHostelId(hostelId)
                .stream()
                .map(FurnitureMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<FurnitureResponseDTO> getByRoom(Long roomId) {
        return furnitureRepository.findByRoomRoomId(roomId)
                .stream()
                .map(FurnitureMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<FurnitureResponseDTO> getBrokenFurniture(Long roomId, FurnitureStatus status) {
        return furnitureRepository.findByRoomRoomIdAndStatus(roomId, status)
                .stream()
                .map(FurnitureMapper::toResponse)
                .collect(Collectors.toList());
    }


    private Furniture getFurnitureOrThrow(Long furnitureId){
        return furnitureRepository.findById(furnitureId)
                .orElseThrow(()->new AppException(ErrorCode.FURNITURE_NOT_FOUND,"Furniture not found with id: "+furnitureId));
    }

}
