package com.hostel.hostel.management.entity;

import com.hostel.hostel.management.enums.PaymentMethod;
import com.hostel.hostel.management.enums.PaymentStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "amount_paid",nullable = false,precision = 10,scale = 2)
    private BigDecimal amountPaid;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method",nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status",nullable = false)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    public Long getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


}
