package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee,Long> {

    List<Fee> findByFeeTypeIgnoreCase(String feeType);


}
