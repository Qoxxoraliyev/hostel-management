package com.hostel.hostel.management.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "furniture")
public class Furniture implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "furniture_id")
    private Long furnitureId;

    @Column(name = "furniture_type",nullable = false,length = 100)
    private String furnitureType;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private Room room;
}
