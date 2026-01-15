package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.FloorRepository;
import com.hostel.hostel.management.repository.RoomRepository;
import com.hostel.hostel.management.service.FloorService;
import com.hostel.hostel.management.service.dto.FloorCreateDTO;
import com.hostel.hostel.management.service.dto.FloorResponseDTO;
import com.hostel.hostel.management.service.mapper.FloorMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;

    private final RoomRepository roomRepository;

    public FloorServiceImpl(FloorRepository floorRepository, RoomRepository roomRepository) {
        this.floorRepository = floorRepository;
        this.roomRepository = roomRepository;
    }



    @Override
    public FloorResponseDTO create(FloorCreateDTO floorCreateDTO){
        Floor floor= FloorMapper.toEntity(floorCreateDTO);
        Floor savedFloor=floorRepository.save(floor);
        return FloorMapper.toResponse(savedFloor);
    }

    @Override
    public FloorResponseDTO update(Long floorId,FloorCreateDTO floorCreateDTO){
        Floor floor=floorRepository.findById(floorId).orElseThrow(()->new AppException(ErrorCode.FLOOR_NOT_FOUND,"Floor not found with id: "+floorId));
        floor.setFloorNumber(floorCreateDTO.floorNumber());
        Floor saved=floorRepository.save(floor);
        return FloorMapper.toResponse(saved);
    }


    @Override
    public FloorResponseDTO getById(Long floorId){
        Floor floor=floorRepository.findById(floorId).orElseThrow(()->new AppException(ErrorCode.FLOOR_NOT_FOUND,"Floor not found with id: "+floorId));
        return FloorMapper.toResponse(floor);
    }


    @Override
    public void delete(Long floorId){
        Floor floor=floorRepository.findById(floorId).orElseThrow(()->new AppException(ErrorCode.FLOOR_NOT_FOUND,"Floor not found with id: "+floorId));
        floorRepository.delete(floor);
    }


    @Override
    public List<FloorResponseDTO> getAll(Pageable pageable){
        return floorRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(FloorMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public int getRoomCount(Long floorId){
        floorRepository.findById(floorId).orElseThrow(()->new AppException(ErrorCode.FLOOR_NOT_FOUND,"Floor not found"));
        return roomRepository.findByFloorId(floorId).size();
    }


    @Override
    @Transactional(readOnly = true)
    public int getEmptyRoomCount(Long floorId){
        floorRepository.findById(floorId).orElseThrow(()->new AppException(ErrorCode.FLOOR_NOT_FOUND,"Floor not found"));
        return roomRepository.findEmptyRoomsFloor(floorId).size();
    }




}
