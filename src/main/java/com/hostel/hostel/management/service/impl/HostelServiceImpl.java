package com.hostel.hostel.management.service.impl;
import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.HostelExpenses;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.HostelExpensesRepository;
import com.hostel.hostel.management.repository.HostelRepository;
import com.hostel.hostel.management.service.HostelService;
import com.hostel.hostel.management.service.dto.HostelCreateDTO;
import com.hostel.hostel.management.service.dto.HostelDetailDTO;
import com.hostel.hostel.management.service.mapper.HostelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HostelServiceImpl implements HostelService {

    private final HostelRepository hostelRepository;

    private final HostelExpensesRepository hostelExpensesRepository;

    public HostelServiceImpl(HostelRepository hostelRepository, HostelExpensesRepository hostelExpensesRepository) {
        this.hostelRepository = hostelRepository;
        this.hostelExpensesRepository = hostelExpensesRepository;
    }

    @Override
    public HostelDetailDTO create(HostelCreateDTO dto){
        Hostel hostel=HostelMapper.toEntity(dto);
        Hostel savedHostel=hostelRepository.save(hostel);
        return HostelMapper.hostelDetailDTO(savedHostel);
    }



    @Override
    public List<HostelDetailDTO> getByName(String name){
        List<Hostel> hostels=hostelRepository.findByNameContainingIgnoreCase(name);

        if (hostels.isEmpty()){
            throw new AppException(ErrorCode.HOSTEL_NOT_FOUND,"Hostel not found with name: "+name);
        }

        return hostels.stream()
                .map(HostelMapper::hostelDetailDTO)
                .toList();
    }



    @Override
    public HostelDetailDTO update(Long hostelId,HostelCreateDTO hostelCreateDTO){
        Hostel hostel=hostelRepository.findById(hostelId).orElseThrow(()->new AppException(ErrorCode.HOSTEL_NOT_FOUND,"Hostel not found with id :"+hostelId));
        hostel.setLocation(hostelCreateDTO.location());
        hostel.setName(hostelCreateDTO.name());
        hostel.setTotalRooms(hostel.getTotalRooms());
        Hostel updatedHostel=hostelRepository.save(hostel);
        return HostelMapper.hostelDetailDTO(updatedHostel);
    }



    @Override
    public HostelDetailDTO getById(Long hostelId){
        Hostel hostel=hostelRepository.findById(hostelId)
                .orElseThrow(()->new AppException(ErrorCode.HOSTEL_NOT_FOUND,"Hostel not found with id :"+hostelId));
        return HostelMapper.hostelDetailDTO(hostel);
    }



    @Override
    public void delete(Long hostelId){
        Hostel hostel=hostelRepository.findById(hostelId)
                .orElseThrow(()->new AppException(ErrorCode.HOSTEL_NOT_FOUND,"Hostel not found with id :"+hostelId));
        hostelRepository.delete(hostel);
    }


    @Override
    public List<HostelDetailDTO> getAll(Pageable pageable){
        return hostelRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(HostelMapper::hostelDetailDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<HostelDetailDTO> getActiveHostels(Pageable pageable){
        return hostelRepository.findByActiveTrue(pageable)
                .getContent()
                .stream()
                .map(HostelMapper::hostelDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getStudentCount(Long hostelId){
        return hostelRepository.countStudentsByHostelId(hostelId);
    }


    @Override
    @Transactional(readOnly = true)
    public Long getEmptyRoomCount(Long hostelId){
        return hostelRepository.countEmptyRoomsByHostelId(hostelId);
    }


    @Override
    @Transactional
    public HostelExpenses addExpense(Long hostelId, String description, BigDecimal amount, Date expenseDate){
        Hostel hostel1=hostelRepository.findById(hostelId)
                .orElseThrow(()->new AppException(ErrorCode.HOSTEL_NOT_FOUND,"Hostel not found"));

        HostelExpenses hostelExpenses=new HostelExpenses();
        hostelExpenses.setDescription(description);
        hostelExpenses.setHostel(hostel1);
        hostelExpenses.setExpenseDate(expenseDate);
        hostelExpenses.setAmount(amount);
        return hostelExpensesRepository.save(hostelExpenses);
    }

    



}
