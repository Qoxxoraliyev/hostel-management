package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.enums.RoomAvailability;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.RoomRepository;
import com.hostel.hostel.management.service.RoomService;
import com.hostel.hostel.management.service.dto.RoomCreateDTO;
import com.hostel.hostel.management.service.dto.RoomResponseDTO;
import com.hostel.hostel.management.service.mapper.RoomMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomResponseDTO create(RoomCreateDTO roomCreateDTO){
        Room room= RoomMapper.toEntity(roomCreateDTO);
        Room saved=roomRepository.save(room);
        return RoomMapper.toResponse(saved);
    }


    @Override
    public RoomResponseDTO update(Long roomId,RoomCreateDTO roomCreateDTO){
        Room room=getRoomOrThrow(roomId);
        room.setRoomNumber(roomCreateDTO.roomNumber());
        room.setCapacity(roomCreateDTO.capacity());
        Room saved=roomRepository.save(room);
        return RoomMapper.toResponse(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public RoomResponseDTO getById(Long roomId){
        return RoomMapper.toResponse(getRoomOrThrow(roomId));
    }


    @Override
    public void delete(Long roomId){
        roomRepository.delete(getRoomOrThrow(roomId));
    }


    @Override
    public List<RoomResponseDTO> getAll(Pageable pageable){
        return roomRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(RoomMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public int getCurrentOccupancy(Long roomId){
        Room room=getRoomOrThrow(roomId);
        return room.getStudents().size();
    }


    @Override
    @Transactional(readOnly = true)
    public RoomAvailability getAvailability(Long roomId){
        Room room=getRoomOrThrow(roomId);
        return room.getStudents().size()<room.getCapacity()
                ?RoomAvailability.AVAILABLE
                :RoomAvailability.FULL;
    }

    private Room getRoomOrThrow(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(()->new AppException(ErrorCode.ROOM_NOT_FOUND,"Room not found with id: "+roomId));
    }


}