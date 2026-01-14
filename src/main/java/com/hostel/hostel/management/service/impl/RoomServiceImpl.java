package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.exceptions.RoomNotFoundException;
import com.hostel.hostel.management.repository.RoomRepository;
import com.hostel.hostel.management.service.RoomService;
import com.hostel.hostel.management.service.dto.RoomCreateDTO;
import com.hostel.hostel.management.service.dto.RoomResponseDTO;
import com.hostel.hostel.management.service.mapper.RoomMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        Room room=roomRepository.findById(roomId).orElseThrow(()->new RoomNotFoundException("Room not found with id: "+roomId));
        room.setRoomNumber(roomCreateDTO.roomNumber());
        room.setCapacity(roomCreateDTO.capacity());
        Room saved=roomRepository.save(room);
        return RoomMapper.toResponse(saved);
    }


    @Override
    public RoomResponseDTO getById(Long roomId){
        Room room=roomRepository.findById(roomId).orElseThrow(()->new RoomNotFoundException("Room not found with id: "+roomId));
        return RoomMapper.toResponse(room);
    }


    @Override
    public void delete(Long roomId){
        Room room=roomRepository.findById(roomId).orElseThrow(()->new RoomNotFoundException("Room not found with id: "+roomId));
        roomRepository.delete(room);
    }


    @Override
    public List<RoomResponseDTO> getAll(Pageable pageable){
        return roomRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(RoomMapper::toResponse)
                .collect(Collectors.toList());
    }


}