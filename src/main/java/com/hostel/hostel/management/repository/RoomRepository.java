package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    List<Room> findByFloorId(Long floorId);

    @Query("""
            SELECT r FROM Room r
            WHERE r.floor.floorId=:floorId
            AND SIZE(r.students)<r.capacity
            """)
    List<Room> findEmptyRoomsFloor(Long floorId);

}
