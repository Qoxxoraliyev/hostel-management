package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Hostel;
import com.hostel.hostel.management.entity.HostelExpenses;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class HostelExpensesRepositoryTests {

    @Autowired
    private HostelExpensesRepository hostelExpensesRepository;

    @Autowired
    private HostelRepository hostelRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setName(name);
        hostel.setActive(true);
        hostel.setLocation("Tashkent");
        hostel.setTotalRooms(100);
        return hostelRepository.save(hostel);
    }


    @Test
    void testFindByHostelHotelId(){
       Hostel hostel=createHostel("Samarkand");
       HostelExpenses hostelExpenses=new HostelExpenses();
       hostelExpenses.setExpenseDate(LocalDate.parse("2026-01-19"));
       hostelExpenses.setHostel(hostel);
       hostelExpenses.setDescription("For 50 new furniture");
       hostelExpenses.setAmount(new BigDecimal("15000.50"));
       hostelExpensesRepository.save(hostelExpenses);
       List<HostelExpenses> result=hostelExpensesRepository.findByHostelHostelId(hostel.getHostelId());
       assertEquals(hostel.getHostelId(),result.get(0).getHostel().getHostelId());
    }


    @Test
    void testFindByHostelHostelIdAndExpensesDateBetween(){
        HostelExpenses hostelExpenses=new HostelExpenses();
        Hostel hostel=createHostel("Namangan");
        hostelExpenses.setAmount(new BigDecimal("12000.20"));
        hostelExpenses.setExpenseDate(LocalDate.parse("2026-03-12"));
        hostelExpenses.setDescription("For foods");
        hostelExpenses.setHostel(hostel);
        hostelExpensesRepository.save(hostelExpenses);
        LocalDate start = LocalDate.of(2026, 3, 1);
        LocalDate end   = LocalDate.of(2026, 3, 31);
        List<HostelExpenses> result=hostelExpensesRepository.findByHostelHostelIdAndExpenseDateBetween(hostel.getHostelId(),start,end);
        assertEquals(1,result.size());
    }


}
