package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Fee;
import com.hostel.hostel.management.enums.FeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee,Long> {

    List<Fee> findByFeeTypeIgnoreCase(String feeType);

    @Query("""
            SELECT f FROM Fee f
            WHERE f.dueDate<CURRENT_DATE
            AND f.status <> 'PAID'
            """)
    List<Fee> findOverdueFees();

    List<Fee> findByStatus(FeeStatus status);
}
