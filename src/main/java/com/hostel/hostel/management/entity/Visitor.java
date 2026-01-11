package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "visitor")
public class Visitor implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitorId;

    private String name;

    @Column(name = "time_in")
    private Time timeIn;

    @Column(name = "time_out")
    private Time timeOut;

    @Column(name = "visit_date")
    private Date visitDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;


}
