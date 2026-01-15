package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

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

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "total_rooms",nullable = false)
    private Integer totalRooms;

    @OneToMany(mappedBy = "hostel")
    private List<Mess> messes;

    @OneToMany(mappedBy = "hostel")
    private List<HostelExpenses> expenses;

    @OneToMany(mappedBy = "hostel")
    private List<Floor> floors;

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

    public List<Mess> getMesses() {
        return messes;
    }

    public void setMesses(List<Mess> messes) {
        this.messes = messes;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<HostelExpenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<HostelExpenses> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString(){
        return "Hostel{"+
                "hostelId="+hostelId+
                ", name='"+name+'\''+
                '}';
    }

}
