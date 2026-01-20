package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.Mess;
import com.hostel.hostel.management.entity.MessEmployee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MessEmployeeRepositoryTests {

    @Autowired
    private MessEmployeeRepository messEmployeeRepository;

    @Autowired
    private MessRepository messRepository;

    @Autowired
    private HostelRepository hostelRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setTotalRooms(100);
        hostel.setName(name);
        hostel.setActive(true);
        hostel.setLocation("Tashkent");
        return hostelRepository.save(hostel);
    }

    private Mess createMess(){
        Hostel hostel=createHostel("Samarkand");
        Mess mess=new Mess();
        mess.setMessTiming("Breakfast at 07:30, Lunch at 13:00, Dinner at 19:00");
        mess.setHostel(hostel);
        mess.setMonthlyExpenses(new BigDecimal("14000.35"));
        return messRepository.save(mess);
    }


    @Test
    void testSavedAndFindById(){
        Mess mess=createMess();

        MessEmployee messEmployee=new MessEmployee();
        messEmployee.setMess(mess);
        messEmployee.setAddress("Andijan");
        messEmployee.setFullName("Davron Ergashev");
        messEmployee.setPhone("+998951551221");
        messEmployee.setSalary(new BigDecimal("560000.45"));
        MessEmployee savedEmployee=messEmployeeRepository.save(messEmployee);
        assertNotNull(savedEmployee.getEmployeeId());
        Optional<MessEmployee> found=messEmployeeRepository.findById(savedEmployee.getEmployeeId());
        assertTrue(found.isPresent());
        assertEquals("Davron Ergashev",found.get().getFullName());
    }


    @Test
    void testDelete(){
        Mess mess=createMess();
        MessEmployee messEmployee=new MessEmployee();
        messEmployee.setSalary(new BigDecimal("560000.45"));
        messEmployee.setPhone("+998905565425");
        messEmployee.setAddress("Namangan");
        messEmployee.setMess(mess);
        messEmployee.setFullName("Ali Valiyev");
        MessEmployee saved=messEmployeeRepository.save(messEmployee);
        messEmployeeRepository.deleteById(saved.getEmployeeId());
        Optional<MessEmployee> result=messEmployeeRepository.findById(messEmployee.getEmployeeId());
        assertFalse(result.isPresent());
    }



}
