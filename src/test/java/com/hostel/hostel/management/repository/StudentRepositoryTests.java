package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.entity.Student;
import com.hostel.hostel.management.enums.CleaningStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StudentRepositoryTests {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private HostelRepository hostelRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setLocation("Andijan");
        hostel.setActive(true);
        hostel.setName("Fergana");
        hostel.setTotalRooms(305);
        return hostelRepository.save(hostel);
    }

    private Floor createFloor(){
        Floor floor=new Floor();
        Hostel hostel=createHostel("Tashkent");
        floor.setFloorNumber(4);
        floor.setHostel(hostel);
        return floorRepository.save(floor);
    }

    private Room createRoom(){
        Room room=new Room();
        Floor floor=createFloor();
        room.setRoomNumber(102);
        room.setCapacity(4);
        room.setCleaningStatus(CleaningStatus.CLEAN);
        room.setFloor(floor);
        return roomRepository.save(room);
    }

    @Test
    void testSavedAndFindById(){
        Room room=createRoom();
        Student student=new Student();
        student.setFullName("Ravshan Temirov");
        student.setAge(23);
        student.setPhone("+998955664515");
        student.setRoom(room);
        student.setDob(LocalDate.of(2006,5,14));
        Student saved=studentRepository.save(student);
        assertNotNull(saved.getStudentId());
        Optional<Student> result=studentRepository.findById(student.getStudentId());
        assertTrue(result.isPresent());
    }


    @Test
    void testDelete(){
        Student student=new Student();
        Room room=createRoom();
        student.setDob(LocalDate.of(2005,5,12));
        student.setRoom(room);
        student.setPhone("+9954547417");
        student.setAge(23);
        student.setFullName("Davron Yo'ladashev");
        Student saved=studentRepository.save(student);
        studentRepository.deleteById(saved.getStudentId());
        Optional<Student> result=studentRepository.findById(student.getStudentId());
        assertFalse(result.isPresent());
    }



}
