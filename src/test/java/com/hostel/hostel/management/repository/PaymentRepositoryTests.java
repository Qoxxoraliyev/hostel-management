package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.*;
import com.hostel.hostel.management.enums.CleaningStatus;
import com.hostel.hostel.management.enums.FeeStatus;
import com.hostel.hostel.management.enums.PaymentMethod;
import com.hostel.hostel.management.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PaymentRepositoryTests {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private FloorRepository floorRepository;

    private Hostel createHostel(String name){
        Hostel hostel=new Hostel();
        hostel.setTotalRooms(100);
        hostel.setLocation("Tashkent");
        hostel.setActive(true);
        hostel.setName("Samarkand");
        return hostelRepository.save(hostel);
    }

    private Floor createFloor(){
        Floor floor=new Floor();
        Hostel hostel=createHostel("Samarkand");
        floor.setFloorNumber(4);
        floor.setHostel(hostel);
        return floorRepository.save(floor);
    }

    private Room createRoom(){
        Room room=new Room();
        Floor floor=createFloor();
        room.setRoomNumber(102);
        room.setFloor(floor);
        room.setCapacity(6);
        room.setCleaningStatus(CleaningStatus.CLEAN);
        return roomRepository.save(room);
    }

    private Student createStudent(){
        Student student=new Student();
        Room room=createRoom();
        student.setRoom(room);
        student.setPhone("+998951474554");
        student.setAge(23);
        student.setDob(LocalDate.of(2025,03,03));
        student.setFullName("Farrux Temirov");
        return studentRepository.save(student);
    }

    private Fee createFee(){
        Fee fee=new Fee();
        Student student=createStudent();
        fee.setStatus(FeeStatus.PAID);
        fee.setStudent(student);
        fee.setDueDate(LocalDate.of(2026,8,15));
        fee.setMonth(new BigDecimal("250000.00"));
        return feeRepository.save(fee);
    }


    @Test
    void testShouldSavedAndFindPaymentById(){
        Payment payment=new Payment();
        Student student=createStudent();
        Fee fee=createFee();
        payment.setPaymentDate(LocalDate.of(2026,1,20));
        payment.setStudent(student);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setAmountPaid(new BigDecimal("25000.50"));
        payment.setPaymentMethod(PaymentMethod.CARD);
        payment.setFee(fee);
        Payment saved=paymentRepository.save(payment);
        assertNotNull(saved.getPaymentId());
        Optional<Payment> result=paymentRepository.findById(saved.getPaymentId());
        assertTrue(result.isPresent());
    }


    @Test
    void testDelete(){
        Payment payment=new Payment();
        Fee fee=createFee();
        payment.setPaymentDate(LocalDate.of(2026,5,25));
        payment.setFee(fee);
        payment.setPaymentMethod(PaymentMethod.CASH);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setAmountPaid(new BigDecimal("25400.23"));
        payment.setStudent(fee.getStudent());
        Payment saved=paymentRepository.save(payment);
        paymentRepository.deleteById(saved.getPaymentId());
        Optional<Payment> result=paymentRepository.findById(payment.getPaymentId());
        assertFalse(result.isPresent());
    }


}
