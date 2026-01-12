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

    public Long getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }


}
