package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.Mess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MessRepositoryTests {

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private MessRepository messRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setName(name);
        hostel.setActive(true);
        hostel.setLocation("Samarkand");
        hostel.setTotalRooms(100);
        return hostelRepository.save(hostel);
    }

    @Test
    void testShouldSaveAndFindMessById(){
        Mess mess=new Mess();
        Hostel hostel=createHostel("Tashkent");
        mess.setMessTiming("Breakfast at 07:30, Lunch at 13:00, Dinner at 19:00");
        mess.setHostel(hostel);
        mess.setMonthlyExpenses(new BigDecimal("560000.14"));
        Mess saved=messRepository.save(mess);
        assertNotNull(saved.getMessId());
        Optional<Mess> result=messRepository.findById(saved.getMessId());
        assertTrue(result.isPresent());
    }

    @Test
    void testDelete(){
        Mess mess=new Mess();
        Hostel hostel=createHostel("Samarkand");
        mess.setMonthlyExpenses(new BigDecimal("450000.23"));
        mess.setMessTiming("Breakfast at 07:30, Lunch at 13:00, Dinner at 19:00");
        mess.setHostel(hostel);
        Mess saved=messRepository.save(mess);
        messRepository.deleteById(mess.getMessId());
        Optional<Mess> result=messRepository.findById(mess.getMessId());
        assertFalse(result.isPresent());
    }




}
