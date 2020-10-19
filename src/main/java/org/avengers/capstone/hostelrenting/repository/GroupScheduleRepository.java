package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.GroupSchedule;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author duattt on 10/17/20
 * @created 17/10/2020 - 15:02
 * @project youthhostelapp
 */
@Repository
public interface GroupScheduleRepository extends JpaRepository<GroupSchedule, Integer> {
}
