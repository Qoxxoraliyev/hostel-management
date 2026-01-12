package com.hostel.hostel.management.service.mapper;

import com.hostel.hostel.management.entity.Payment;
import com.hostel.hostel.management.service.dto.PaymentCreateDTO;
import com.hostel.hostel.management.service.dto.PaymentResponseDTO;

public class PaymentMapper {

    public static Payment toEntity(PaymentCreateDTO dto){
        Payment p=new Payment();
        p.setAmountPaid(dto.amountPaid());
        p.setPaymentDate(dto.paymentDate());
        p.setPaymentStatus(dto.paymentStatus());
        p.setPaymentMethod(dto.paymentMethod());
        return p;
    }

    public static PaymentResponseDTO toResponse(Payment p){
        return new PaymentResponseDTO(
                p.getPaymentId(),
                p.getAmountPaid(),
                p.getPaymentDate(),
                p.getPaymentMethod(),
                p.getPaymentStatus()
        );
    }



}
