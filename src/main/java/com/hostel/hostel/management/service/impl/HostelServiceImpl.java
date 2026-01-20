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
import com.hostel.hostel.management.service.dto.HostelExpensesResponseDTO;
import com.hostel.hostel.management.service.mapper.HostelExpenseMapper;
import com.hostel.hostel.management.service.mapper.HostelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
    @Transactional(readOnly = true)
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
        Hostel hostel=getHostelOrThrow(hostelId);
        hostel.setLocation(hostelCreateDTO.location());
        hostel.setName(hostelCreateDTO.name());
        hostel.setTotalRooms(hostelCreateDTO.totalRooms());
        Hostel updatedHostel=hostelRepository.save(hostel);
        return HostelMapper.hostelDetailDTO(updatedHostel);
    }



    @Override
    @Transactional(readOnly = true)
    public HostelDetailDTO getById(Long hostelId){
        return HostelMapper.hostelDetailDTO(getHostelOrThrow(hostelId));
    }



    @Override
    public void delete(Long hostelId){
        hostelRepository.delete(getHostelOrThrow(hostelId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<HostelDetailDTO> getAll(Pageable pageable){
        return hostelRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(HostelMapper::hostelDetailDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
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
    public HostelExpensesResponseDTO addExpense(Long hostelId, String description, BigDecimal amount, LocalDate expenseDate){
        Hostel hostel1=getHostelOrThrow(hostelId);
        HostelExpenses hostelExpenses=new HostelExpenses();
        hostelExpenses.setDescription(description);
        hostelExpenses.setHostel(hostel1);
        hostelExpenses.setExpenseDate(expenseDate);
        hostelExpenses.setAmount(amount);
        HostelExpenses saved=hostelExpensesRepository.save(hostelExpenses);
        return HostelExpenseMapper.hostelExpensesResponseDTO(saved);
    }


    @Override
    public BigDecimal getMonthlyExpenses(Long hostelId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<HostelExpenses> expenses =
                hostelExpensesRepository
                        .findByHostelHostelIdAndExpenseDateBetween(
                                hostelId, start, end
                        );
        return expenses.stream()
                .map(HostelExpenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }




    @Override
    public BigDecimal getYearlyExpenses(Long hostelId, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        List<HostelExpenses> expenses =
                hostelExpensesRepository
                        .findByHostelHostelIdAndExpenseDateBetween(
                                hostelId, start, end
                        );
        return expenses.stream()
                .map(HostelExpenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }



    @Override
    public List<HostelExpensesResponseDTO> getExpenses(Long hostelId) {
        List<HostelExpenses> expenses =
                hostelExpensesRepository.findByHostelHostelId(hostelId);
        if (expenses.isEmpty()) {
            throw new RuntimeException("Expenses not found for hostel id: " + hostelId);
        }
        return expenses.stream()
                .map(HostelExpenseMapper::hostelExpensesResponseDTO)
                .toList();
    }


    private Hostel getHostelOrThrow(Long hostelId){
        return hostelRepository.findById(hostelId)
                .orElseThrow(()->new AppException(ErrorCode.HOSTEL_NOT_FOUND,"Hostel not found with id: "+hostelId));
    }



}
