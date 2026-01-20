package com.hostel.hostel.management.repository;
import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.entity.Student;
import com.hostel.hostel.management.enums.CleaningStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class HostelRepositoryTests {

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setName(name);
        hostel.setLocation("Andijan");
        hostel.setTotalRooms(12);
        hostel.setActive(true);
        return hostelRepository.save(hostel);
    }

    private Student createStudent(Room room){
        Student student=new Student();
        Calendar calendar=Calendar.getInstance();
        calendar.set(2005,Calendar.JANUARY,15);
        Date dob = calendar.getTime();
        student.setRoom(room);
        student.setAge(18);
        student.setDob(LocalDate.of(2025,05,04));
        student.setFullName("Davron Kabulov");
        student.setPhone("+998910447417");
        return studentRepository.save(student);
    }


    private Floor createFloor(Hostel hostel){
        Floor floor=new Floor();
        floor.setHostel(hostel);
        floor.setFloorNumber(2);
        return floorRepository.save(floor);
    }

    private Room createRoom(Floor floor){
        Room room=new Room();
        room.setRoomNumber(102);
        room.setCapacity(6);
        room.setFloor(floor);
        room.setCleaningStatus(CleaningStatus.CLEAN);
        return roomRepository.save(room);
    }


    @Test
    void testFindByNameContainingIgnoreCase(){
        Hostel hostel1=createHostel("Andijan");
        Hostel hostel2=createHostel("Fergana");
        List<Hostel> result=hostelRepository.findByNameContainingIgnoreCase("fergana");
        assertEquals(1,result.size());
        assertEquals("Fergana",result.get(0).getName());
    }


    @Test
    void testFindByActiveTrue(){
        Hostel hostel1=createHostel("Namangan");
        Hostel hostel2=createHostel("Fergana");
        Pageable pageable= PageRequest.of(0,10);

        Page<Hostel> hostels=hostelRepository.findByActiveTrue(pageable);
        assertEquals(2,hostels.getContent().size());
        assertTrue(hostels.getContent().stream().allMatch(Hostel::isActive));
    }


    @Test
    void testCountStudentsByHostelId(){
        Hostel hostel=createHostel("Samarkand");
        Floor floor=createFloor(hostel);
        Room room=createRoom(floor);

        createStudent(room);
        createStudent(room);

        Long result=hostelRepository.countStudentsByHostelId(hostel.getHostelId());
        assertEquals(2L,result);
    }

    @Test
    void  testCountEmptyRoomsByHostelId(){
        Hostel hostel=createHostel("Fergana");
        Floor floor=createFloor(hostel);

        Room occupiedRoom=createRoom(floor);
        createStudent(occupiedRoom);
        createRoom(floor);

        Long result=hostelRepository.countEmptyRoomsByHostelId(hostel.getHostelId());
        assertEquals(1L,result);


    }

}
