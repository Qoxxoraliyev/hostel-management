package com.hostel.hostel.management.service;


import com.hostel.hostel.management.entity.HostelExpenses;
import com.hostel.hostel.management.service.dto.HostelCreateDTO;
import com.hostel.hostel.management.service.dto.HostelDetailDTO;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface HostelService {

    HostelDetailDTO create(HostelCreateDTO hostelCreateDTO);

    List<HostelDetailDTO> getByName(String name);

    HostelDetailDTO update(Long hostelId,HostelCreateDTO hostelCreateDTO);

    HostelDetailDTO getById(Long hostelId);

    void delete(Long hostelId);

    List<HostelDetailDTO> getAll(Pageable pageable);

    List<HostelDetailDTO> getActiveHostels(Pageable pageable);

    Long getStudentCount(Long hostelId);

    Long getEmptyRoomCount(Long hostelId);

    HostelExpenses addExpense(Long hostelId, String description, BigDecimal amount, Date expenseDate);

    List<HostelExpenses> getExpenses(Long hostelId);

    BigDecimal getMonthlyExpenses(Long hostelId,int year,int month);

    BigDecimal getYearlyExpenses(Long hostelId,int year);

}
