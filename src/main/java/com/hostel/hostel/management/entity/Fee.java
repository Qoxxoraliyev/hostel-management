package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @Column(name = "fee_type",nullable = false,length = 50)
    private String feeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    @OneToMany(mappedBy = "fee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payment> payments;
}
