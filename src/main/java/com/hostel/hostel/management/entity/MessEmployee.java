package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "mess_employee")
public class MessEmployee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "full_name")
    private String fullName;

    private String phone;

    private BigDecimal salary;

    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mess_id",nullable = false)
    private Mess mess;
}
