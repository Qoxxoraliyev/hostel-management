package com.hostel.hostel.management.repository;

import com.hostel.hostel.management.entity.HostelExpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HostelExpensesRepository extends JpaRepository<HostelExpenses,Long> {

    List<HostelExpenses> findByHostelHostelId(Long hostelId);

    List<HostelExpenses> findByHostelHostelIdAndExpenseDateBetween(Long hostelId, Date startDate,Date endDate);

}
