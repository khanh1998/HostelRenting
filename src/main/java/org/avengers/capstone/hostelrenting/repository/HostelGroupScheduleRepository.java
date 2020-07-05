package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelGroupSchedule;
import org.avengers.capstone.hostelrenting.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelGroupScheduleRepository extends JpaRepository<HostelGroupSchedule, Integer> {
    @Query("SELECT h from HostelGroup h where h.hostelGroupId in (SELECT g.hostelGroup.hostelGroupId from HostelGroupSchedule g where g.schedule.scheduleId = ?1)")
    List<HostelGroup> findAllHostelGroupByScheduleId(Integer scheduleId);

    @Query("SELECT s from Schedule s where s.scheduleId in (SELECT g.schedule.scheduleId from HostelGroupSchedule g where g.hostelGroup.hostelGroupId = ?1)")
    List<Schedule> findAllScheduleByHostelGroupId(Integer hostelGroupId);
}
