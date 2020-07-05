package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
//    List<Schedule> findAllByHostelGroups_HostelGroupId(int hostelGroupId);
}
