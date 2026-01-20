package com.hostel.hostel.management.repository;
import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.enums.CleaningStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class RoomRepositoryTests {

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FloorRepository floorRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setName(name);
        hostel.setTotalRooms(100);
        hostel.setActive(true);
        hostel.setLocation("Samarkand");
        return hostelRepository.save(hostel);
    }

    private Floor createFloor(){
        Floor floor=new Floor();
        Hostel hostel=createHostel("Samarkand");
        floor.setHostel(hostel);
        floor.setFloorNumber(3);
        return floorRepository.save(floor);
    }


    @Test
    void  testSavedAndFindById(){
        Room room=new Room();
        Floor floor=createFloor();
        room.setRoomNumber(100);
        room.setFloor(floor);
        room.setCapacity(6);
        room.setCleaningStatus(CleaningStatus.CLEAN);
        Room saved=roomRepository.save(room);
        assertNotNull(saved.getRoomId());
        Optional<Room> result=roomRepository.findById(room.getRoomId());
        assertFalse(result.isPresent());
    }


    @Test
    void testDelete(){
        Room room=new Room();
        Floor floor=createFloor();
        room.setCleaningStatus(CleaningStatus.CLEAN);
        room.setCapacity(4);
        room.setRoomNumber(103);
        room.setFloor(floor);
        Floor saved=floorRepository.save(floor);
        floorRepository.deleteById(floor.getFloorId());
        Optional<Floor> result=floorRepository.findById(floor.getFloorId());
        assertFalse(result.isPresent());
    }


}
