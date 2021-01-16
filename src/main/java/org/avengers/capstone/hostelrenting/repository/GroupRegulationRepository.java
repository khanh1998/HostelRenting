package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.GroupRegulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * @author duattt on 11/14/20
 * @created 14/11/2020 - 09:59
 * @project youthhostelapp
 */
public interface GroupRegulationRepository extends JpaRepository<GroupRegulation, Integer> {
    Collection<GroupRegulation> findByGroup_GroupIdAndRegulation_IsApproved(Integer groupId, boolean isApproved);

    Collection<GroupRegulation> findByGroup_GroupId(Integer groupId);
}
