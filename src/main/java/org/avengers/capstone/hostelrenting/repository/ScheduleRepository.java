package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s from Schedule s where s.vendor in (SELECT v from Vendor v where v.vendorId = ?1)")
    List<Schedule> findAllScheduleByVendorId(Integer vendorId);
}
