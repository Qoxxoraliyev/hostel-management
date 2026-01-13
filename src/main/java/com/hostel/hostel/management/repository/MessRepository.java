package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessRepository extends JpaRepository<Mess,Long> {
}
