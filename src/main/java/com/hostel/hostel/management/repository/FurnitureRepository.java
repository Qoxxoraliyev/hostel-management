package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Furniture;
import com.hostel.hostel.management.enums.FurnitureStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture,Long> {

    List<Furniture> findByQuantityLessThan(Integer threshold);


    @Query("""
        SELECT f FROM Furniture f
        WHERE f.room.floor.hostel.hostelId = :hostelId
    """)
    List<Furniture> findAllByHostelId(Long hostelId);

    List<Furniture> findByRoomRoomId(Long roomId);

    List<Furniture> findByRoomRoomIdAndStatus(Long roomId, FurnitureStatus status);
}
