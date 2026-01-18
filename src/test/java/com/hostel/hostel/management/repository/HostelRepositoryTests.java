package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Hostel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class HostelRepositoryTests {

    @Autowired
    private HostelRepository hostelRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setName(name);
        hostel.setLocation("Andijan");
        hostel.setTotalRooms(12);
        hostel.setActive(true);
        return hostelRepository.save(hostel);
    }


    @Test
    void testFindByNameContainingIgnoreCase(){
        Hostel hostel1=createHostel("Andijan");
        Hostel hostel2=createHostel("Fergana");
        List<Hostel> result=hostelRepository.findByNameContainingIgnoreCase("fergana");
        assertEquals(1,result.size());
        assertEquals("Fergana",result.get(0).getName());
    }






}
