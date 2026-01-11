package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_number",nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Student> students;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id",nullable = false)
    private Floor floor;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Furniture> furnitures;


}
