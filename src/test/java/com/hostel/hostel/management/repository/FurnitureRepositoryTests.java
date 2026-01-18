package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.entity.Furniture;
import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.Room;
import com.hostel.hostel.management.enums.FurnitureStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class FurnitureRepositoryTests {

    @Autowired
    private FurnitureRepository furnitureRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Floor createFloor(){
        Hostel hostel=new Hostel();
        hostel.setName("Test Hostel");
        hostel.setLocation("Tashkent");
        hostel.setTotalRooms(10);
        hostel.setActive(true);
        hostel=hostelRepository.save(hostel);

        Floor floor=new Floor();
        floor.setFloorNumber(1);
        floor.setHostel(hostel);

        return floorRepository.save(floor);
    }


    private Room createRoom(){
        Floor floor=createFloor();

        Room room=new Room();
        room.setRoomNumber(101);
        room.setCapacity(6);
        room.setFloor(floor);

        return roomRepository.save(room);
    }


    private Furniture createFurniture(Room room,Integer quantity){
        Furniture furniture=new Furniture();
        furniture.setQuantity(quantity);
        furniture.setRoom(room);
        furniture.setStatus(FurnitureStatus.OK);
        furniture.setFurnitureType("Bed");

        return furnitureRepository.save(furniture);
    }


    @Test
    void testFindByQuantityLessThan(){
        Room room=createRoom();

        createFurniture(room,2);
        createFurniture(room,10);

        List<Furniture> result=furnitureRepository.findByQuantityLessThan(5);

        assertEquals(1,result.size());
        assertTrue(result.get(0).getQuantity()<5);
    }


    @Test
    void testFindByRoom(){
        Room room=createRoom();

        createFurniture(room,3);
        createFurniture(room,4);

        List<Furniture> furnitures=furnitureRepository.findByRoomRoomId(room.getRoomId());
        assertEquals(2,furnitures.size());
    }


    @Test
    void testFindByRoomRoomIdAndStatus(){
        Room room=createRoom();

        Furniture f1=createFurniture(room,2);
        Furniture f2=createFurniture(room,5);

        f2.setStatus(FurnitureStatus.BROKEN);
        furnitureRepository.save(f2);

        List<Furniture> okFurnitures=furnitureRepository.findByRoomRoomIdAndStatus(room.getRoomId(),FurnitureStatus.OK);

        assertEquals(1,okFurnitures.size());
        assertEquals(FurnitureStatus.OK,okFurnitures.get(0).getStatus());

    }


    @Test
    void testFindAllByHostelId(){
        Room room=createRoom();

        createFurniture(room,3);
        createFurniture(room,6);

        Long hostelId=room.getFloor().getHostel().getHostelId();
        List<Furniture> result=furnitureRepository.findAllByHostelId(hostelId);
        assertEquals(2,result.size());
    }



}
