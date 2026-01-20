package com.hostel.hostel.management.repository;


import com.hostel.hostel.management.entity.*;
import com.hostel.hostel.management.enums.CleaningStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VisitorRepositoryTests {

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setTotalRooms(120);
        hostel.setName(name);
        hostel.setActive(true);
        hostel.setLocation("Fergana");
        return hostelRepository.save(hostel);
    }

    private Floor createFloor(){
        Floor floor=new Floor();
        Hostel hostel=createHostel("Andijan");
        floor.setFloorNumber(4);
        floor.setHostel(hostel);
        return floorRepository.save(floor);
    }

    private Room createRoom(){
        Room room=new Room();
        Floor floor=createFloor();
        room.setFloor(floor);
        room.setRoomNumber(223);
        room.setCapacity(4);
        room.setCleaningStatus(CleaningStatus.CLEAN);
        return roomRepository.save(room);
    }

    private Student createStudent(){
        Student student=new Student();
        Room room=createRoom();
        student.setFullName("Ravshan Farruxov");
        student.setAge(24);
        student.setPhone("+9978451221");
        student.setRoom(room);
        student.setDob(LocalDate.of(2006,2,23));
        return studentRepository.save(student);
    }


    @Test
    void testSavedAndFindById(){
        Visitor visitor=new Visitor();
        Student student=createStudent();
        visitor.setTimeIn(Time.valueOf(LocalTime.of(9,0)));
        visitor.setTimeOut(Time.valueOf(LocalTime.of(17,30)));
        visitor.setVisitDate(LocalDate.of(2026,5,5));
        visitor.setStudent(student);
        visitor.setName("Eldor");
        Visitor saved=visitorRepository.save(visitor);
        assertNotNull(saved.getVisitorId());
        Optional<Visitor> result=visitorRepository.findById(visitor.getVisitorId());
        assertTrue(result.isPresent());
    }


    @Test
    void testDelete(){
        Visitor visitor=new Visitor();
        Student student=createStudent();
        visitor.setName("Kamron");
        visitor.setStudent(student);
        visitor.setVisitDate(LocalDate.of(2026,12,23));
        visitor.setTimeIn(Time.valueOf(LocalTime.of(10,15)));
        visitor.setTimeOut(Time.valueOf(LocalTime.of(14,25)));
        Visitor saved=visitorRepository.save(visitor);
        visitorRepository.deleteById(visitor.getVisitorId());
        Optional<Visitor> result=visitorRepository.findById(visitor.getVisitorId());
        assertFalse(result.isPresent());
    }


}
