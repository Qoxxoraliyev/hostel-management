package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "mess")
public class Mess implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mess_id")
    private Long messId;

    @Column(name = "monthly_expenses",precision = 10,scale = 2)
    private BigDecimal monthlyExpenses;

    @Column(name = "mess_timing",length = 100)
    private String messTiming;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id",nullable = false)
    private Hostel hostel;

    @OneToMany(mappedBy = "mess")
    private List<MessEmployee> messEmployees;

    public Mess(){}

    public Long getMessId() {
        return messId;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public String getMessTiming() {
        return messTiming;
    }

    public void setMessTiming(String messTiming) {
        this.messTiming = messTiming;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public List<MessEmployee> getMessEmployees() {
        return messEmployees;
    }

    public void setMessEmployees(List<MessEmployee> messEmployees) {
        this.messEmployees = messEmployees;
    }

    @Override
    public String toString() {
        return "Mess{" +
                "messId=" + messId +
                ", monthlyExpenses=" + monthlyExpenses +
                ", messTiming='" + messTiming + '\'' +
                '}';
    }



}
