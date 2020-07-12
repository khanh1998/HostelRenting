package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
