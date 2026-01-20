package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Fee;
import com.hostel.hostel.management.enums.FeeStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeeRepositoryTests {

    @Mock
    private FeeRepository feeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByStatus() {
        Fee fee1 = new Fee();
        fee1.setStatus(FeeStatus.PAID);
        fee1.setMonth(BigDecimal.valueOf(100));

        Fee fee2 = new Fee();
        fee2.setStatus(FeeStatus.PAID);
        fee2.setMonth(BigDecimal.valueOf(200));

        List<Fee> paidFees = new ArrayList<>();
        paidFees.add(fee1);
        paidFees.add(fee2);

        when(feeRepository.findByStatus(FeeStatus.PAID))
                .thenReturn(paidFees);

        List<Fee> result = feeRepository.findByStatus(FeeStatus.PAID);

        assertEquals(2, result.size());
        assertEquals(FeeStatus.PAID, result.get(0).getStatus());
        assertEquals(FeeStatus.PAID, result.get(1).getStatus());

        verify(feeRepository, times(1))
                .findByStatus(FeeStatus.PAID);
    }


    @Test
    public void testFindOverdueFees() {
        Fee overdueFee = new Fee();
        overdueFee.setStatus(FeeStatus.UNPAID);
        overdueFee.setDueDate(LocalDate.of(2026,05,05));

        List<Fee> overdueFees = new ArrayList<>();
        overdueFees.add(overdueFee);

        when(feeRepository.findOverdueFees())
                .thenReturn(overdueFees);

        List<Fee> result = feeRepository.findOverdueFees();

        assertEquals(1, result.size());
        assertEquals(FeeStatus.UNPAID, result.get(0).getStatus());

        verify(feeRepository, times(1))
                .findOverdueFees();
    }



}
