package com.hostel.hostel.management.service.mapper;


import com.hostel.hostel.management.entity.Fee;
import com.hostel.hostel.management.service.dto.FeeCreateDTO;
import com.hostel.hostel.management.service.dto.FeeResponseDTO;


public class FeeMapper {

    public static Fee toEntity(FeeCreateDTO dto){
        Fee f=new Fee();
        f.setMonth(dto.month());
        f.setFeeType(dto.feeType());
        return f;
    }


    public static FeeResponseDTO toResponse(Fee fee){
        return new FeeResponseDTO(
                fee.getFeeId(),
                fee.getMonth(),
                fee.getFeeType(),
                fee.getPayments().stream().map(PaymentMapper::toResponse).toList()
        );
    }



}
