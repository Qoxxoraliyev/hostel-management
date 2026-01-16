package com.hostel.hostel.management.service;

import com.hostel.hostel.management.service.dto.PaymentCreateDTO;
import com.hostel.hostel.management.service.dto.PaymentResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO create(PaymentCreateDTO paymentCreateDTO);

    PaymentResponseDTO update(Long paymentId,PaymentCreateDTO paymentCreateDTO);

    PaymentResponseDTO getById(Long paymentId);

    void delete(Long paymentId);

    List<PaymentResponseDTO> getAll(Pageable pageable);

    byte[] generateReceiptPdf(Long paymentId);

}
