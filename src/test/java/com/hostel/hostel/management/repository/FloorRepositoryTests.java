package com.hostel.hostel.management.repository;


import com.hostel.hostel.management.entity.Floor;
import com.hostel.hostel.management.entity.Hostel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FloorRepositoryTests {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Test
    void testSaveAndFindFloor(){
        Hostel hostel=new Hostel();
        hostel.setName("Test Hostel");
        hostel=hostelRepository.save(hostel);
    }

    Floor floor=new Floor();

}
