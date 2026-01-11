package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
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

    @Column(name = "full_name",nullable = false,length =150)
    private String fullName;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 20)
    private String phone;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Fee> fees;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "student")
    private List<Visitor> visitors;


}
