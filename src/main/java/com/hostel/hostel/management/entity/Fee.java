package com.hostel.hostel.management.entity;

import com.hostel.hostel.management.enums.FeeStatus;
import jakarta.persistence.*;
import jakarta.persistence.Temporal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "fee")
public class Fee implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private Long feeId;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal month;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeeStatus status=FeeStatus.UNPAID;

    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    @OneToMany(mappedBy = "fee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payment> payments;

    public Long getFeeId() {
        return feeId;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public FeeStatus getStatus() {
        return status;
    }

    public void setStatus(FeeStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

}
