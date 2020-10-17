package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.GroupSchedule;
import org.avengers.capstone.hostelrenting.model.GroupService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * @author duattt on 10/17/20
 * @created 17/10/2020 - 15:02
 * @project youthhostelapp
 */
public interface GroupScheduleRepository extends JpaRepository<GroupSchedule, Integer> {
    Collection<GroupSchedule> getByGroup_GroupId(Integer groupId);
}
