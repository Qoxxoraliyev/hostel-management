package com.hostel.hostel.management.repository;


import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.entity.Hostel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FloorRepositoryTests {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private HostelRepository hostelRepository;

    private Hostel createHostel(String name){
        Hostel hostel = new Hostel();
        hostel.setName(name);
        hostel.setLocation("Tashkent");
        hostel.setTotalRooms(10);
        hostel.setActive(true);
        return hostelRepository.save(hostel);
    }

    @Test
    void testSaveAndFindFloor(){
        Hostel hostel=createHostel("Test Hostel");

        Floor floor=new Floor();
        floor.setFloorNumber(1);
        floor.setHostel(hostel);

        Floor savedFloor=floorRepository.save(floor);

        assertNotNull(savedFloor.getFloorId(),"Floor ID should not be null after save");
        assertEquals(1,savedFloor.getFloorNumber());
        assertEquals(hostel.getHostelId(),savedFloor.getHostel().getHostelId());

        Optional<Floor> foundFloor=floorRepository.findById(savedFloor.getFloorId());
        assertTrue(foundFloor.isPresent());
        assertEquals(1,foundFloor.get().getFloorNumber());

    }


    @Test
    void testFindAllFloors(){
        Hostel hostel=createHostel("Another Hostel");

        Floor floor1 = new Floor();
        floor1.setFloorNumber(1);
        floor1.setHostel(hostel);
        floorRepository.save(floor1);

        Floor floor2 = new Floor();
        floor2.setFloorNumber(2);
        floor2.setHostel(hostel);
        floorRepository.save(floor2);

        List<Floor> floors=floorRepository.findAll();
        assertEquals(2,floors.size());
    }


    @Test
    void testDeleteFloor(){
        Hostel hostel=createHostel("Delete Hostel");

        Floor floor=new Floor();
        floor.setFloorNumber(5);
        floor.setHostel(hostel);
        floor=floorRepository.save(floor);

        floorRepository.delete(floor);

        Optional<Floor> deletefloor=floorRepository.findById(floor.getFloorId());
        assertFalse(deletefloor.isPresent(),"Floor should be deleted");
    }






}
