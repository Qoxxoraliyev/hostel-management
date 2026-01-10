package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel,Long> {

    List<Hostel> findByNameContainingIgnoreCase(String name);


}
