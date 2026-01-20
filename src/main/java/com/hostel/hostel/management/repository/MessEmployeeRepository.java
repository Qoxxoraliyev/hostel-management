package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.MessEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessEmployeeRepository extends JpaRepository<MessEmployee,Long> {

}
