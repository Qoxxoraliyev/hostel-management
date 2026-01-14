package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Payment;
import com.hostel.hostel.management.exceptions.PaymentNotFoundException;
import com.hostel.hostel.management.repository.PaymentRepository;
import com.hostel.hostel.management.service.PaymentService;
import com.hostel.hostel.management.service.dto.PaymentCreateDTO;
import com.hostel.hostel.management.service.dto.PaymentResponseDTO;
import com.hostel.hostel.management.service.mapper.PaymentMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public PaymentResponseDTO create(PaymentCreateDTO paymentCreateDTO){
        Payment payment= PaymentMapper.toEntity(paymentCreateDTO);
        Payment saved=paymentRepository.save(payment);
        return PaymentMapper.toResponse(saved);
    }


    @Override
    public PaymentResponseDTO update(Long paymentId,PaymentCreateDTO paymentCreateDTO){
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment not found with id: "+paymentId));
        payment.setPaymentMethod(paymentCreateDTO.paymentMethod());
        payment.setPaymentStatus(paymentCreateDTO.paymentStatus());
        payment.setPaymentDate(paymentCreateDTO.paymentDate());
        payment.setAmountPaid(paymentCreateDTO.amountPaid());
        Payment saved=paymentRepository.save(payment);
        return PaymentMapper.toResponse(saved);
    }



    @Override
    public PaymentResponseDTO getById(Long paymentId){
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment not found with id: "+paymentId));
        return PaymentMapper.toResponse(payment);
    }


    @Override
    public void delete(Long paymentId){
        Payment payment=paymentRepository.findById(paymentId).orElseThrow(()->new PaymentNotFoundException("Payment not found with id: "+paymentId));
        paymentRepository.delete(payment);
    }


    @Override
    public List<PaymentResponseDTO> getAll(Pageable pageable){
        return paymentRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }


}
