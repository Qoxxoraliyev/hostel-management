package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.HostelExpenses;
import com.hostel.hostel.management.service.dto.HostelExpensesCreateDTO;
import com.hostel.hostel.management.service.dto.HostelExpensesResponseDTO;

public class HostelExpenseMapper {


    public static HostelExpenses toEntity(HostelExpensesCreateDTO hostelExpensesCreateDTO){
        HostelExpenses hostelExpenses=HostelExpenseMapper.toEntity(hostelExpensesCreateDTO);
        hostelExpenses.setExpenseDate(hostelExpensesCreateDTO.expenseDate());
        hostelExpenses.setAmount(hostelExpensesCreateDTO.amount());
        hostelExpenses.setDescription(hostelExpensesCreateDTO.description());
        return hostelExpenses;
    }


    public static HostelExpensesResponseDTO hostelExpensesResponseDTO(HostelExpenses hostelExpenses){
        return new HostelExpensesResponseDTO(
                hostelExpenses.getId(),
                hostelExpenses.getDescription(),
                hostelExpenses.getAmount(),
                hostelExpenses.getExpenseDate()
        );
    }



}
