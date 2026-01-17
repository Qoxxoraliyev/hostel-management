package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.Payment;
import com.hostel.hostel.management.enums.ErrorCode;
import com.hostel.hostel.management.exceptions.AppException;
import com.hostel.hostel.management.repository.PaymentRepository;
import com.hostel.hostel.management.service.PaymentService;
import com.hostel.hostel.management.service.dto.PaymentCreateDTO;
import com.hostel.hostel.management.service.dto.PaymentResponseDTO;
import com.hostel.hostel.management.service.mapper.PaymentMapper;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
        Payment payment=getPaymentOrThrow(paymentId);
        payment.setPaymentMethod(paymentCreateDTO.paymentMethod());
        payment.setPaymentStatus(paymentCreateDTO.paymentStatus());
        payment.setPaymentDate(paymentCreateDTO.paymentDate());
        payment.setAmountPaid(paymentCreateDTO.amountPaid());
        Payment saved=paymentRepository.save(payment);
        return PaymentMapper.toResponse(saved);
    }



    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getById(Long paymentId){
        return PaymentMapper.toResponse(getPaymentOrThrow(paymentId));
    }


    @Override
    public void delete(Long paymentId){
        paymentRepository.delete(getPaymentOrThrow(paymentId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponseDTO> getAll(Pageable pageable){
        return paymentRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateReceiptPdf(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new AppException(ErrorCode.PAYMENT_NOT_FOUND, "Payment not found"));

        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font valueFont = new Font(Font.HELVETICA, 12);

            Paragraph title = new Paragraph("PAYMENT RECEIPT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Receipt ID: " + payment.getPaymentId(), valueFont));
            document.add(new Paragraph("Payment Date: " + payment.getPaymentDate(), valueFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("STUDENT INFORMATION", labelFont));

            document.add(new Paragraph(
                    "Full Name: " + payment.getStudent().getFullName(), valueFont
            ));
            document.add(new Paragraph(
                    "Room Number: " + payment.getStudent().getRoom().getRoomNumber(), valueFont
            ));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("PAYMENT DETAILS", labelFont));

            document.add(new Paragraph(
                    "Amount Paid: " + payment.getAmountPaid(), valueFont
            ));
            document.add(new Paragraph(
                    "Payment Method: " + payment.getPaymentMethod(), valueFont
            ));
            document.add(new Paragraph(
                    "Payment Status: " + payment.getPaymentStatus(), valueFont
            ));


            if (payment.getFee() != null) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph("FEE INFORMATION", labelFont));

                document.add(new Paragraph(
                        "Monthly Fee Amount: " + payment.getFee().getMonth(), valueFont
                ));
                document.add(new Paragraph(
                        "Fee Status: " + payment.getFee().getStatus(), valueFont
                ));
                document.add(new Paragraph(
                        "Due Date: " + payment.getFee().getDueDate(), valueFont
                ));
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Thank you for your payment.", valueFont));

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new AppException(
                    ErrorCode.PDF_GENERATION_FAILED,
                    "Failed to generate payment receipt"
            );
        }
    }


    private Payment getPaymentOrThrow(Long paymentId){
        return paymentRepository.findById(paymentId)
                .orElseThrow(()->new AppException(ErrorCode.PAYMENT_NOT_FOUND,"Payment not found with id: "+paymentId));
    }

    private void addParagraph(Document doc,String text,Font font) throws DocumentException{
        Paragraph p=new Paragraph(text,font);
        p.setSpacingAfter(5);
        doc.add(p);
    }

    private void addParagraph(Document doc,String text,Font font,int alignment) throws DocumentException{
        Paragraph p=new Paragraph(text,font);
        p.setAlignment(alignment);
        p.setSpacingAfter(5);
        doc.add(p);
    }


}
