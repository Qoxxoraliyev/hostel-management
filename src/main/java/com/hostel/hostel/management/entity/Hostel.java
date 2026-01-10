package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "hostel")
public class Hostel implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hostel_id")
    private Long hostelId;

    @Column(name = "name",nullable = false,length = 100)
    private String name;

    @Column(name = "location",nullable = false,length = 150)
    private String location;

    @Column(name = "total_rooms",nullable = false)
    private Integer totalRooms;

    @Column(name = "annual_expenses",precision = 12,scale = 2)
    private BigDecimal annualExpenses;


    public Hostel(){}

    public Long getHostelId() {
        return hostelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(Integer totalRooms) {
        this.totalRooms = totalRooms;
    }

    public BigDecimal getAnnualExpenses() {
        return annualExpenses;
    }

    public void setAnnualExpenses(BigDecimal annualExpenses) {
        this.annualExpenses = annualExpenses;
    }


    @Override
    public String toString(){
        return "Hostel{"+
                "hostelId="+hostelId+
                ", name='"+name+'\''+
                '}';
    }

}
