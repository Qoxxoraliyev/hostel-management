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

    @Column(name = "full_name",nullable = false,length = 150)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(precision = 10,scale = 2)
    private BigDecimal salary;

    @Column(length = 255)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mess_id",nullable = false)
    private Mess mess;

    public MessEmployee(){}

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Mess getMess() {
        return mess;
    }

    public void setMess(Mess mess) {
        this.mess = mess;
    }


}
