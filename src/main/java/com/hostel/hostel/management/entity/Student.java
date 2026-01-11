package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "full_name")
    private String fullName;

    private Integer age;

    private String phone;

    private Date dob;

    private BigInteger room_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

    @OneToMany(mappedBy = "student")
    private List<Fee> fees;

    @OneToMany(mappedBy = "student")
    private List<Payment> payments;

    @OneToMany(mappedBy = "student")
    private List<Visitor> visitors;


}
