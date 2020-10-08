package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.GroupService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author duattt on 9/26/20
 * @created 26/09/2020 - 20:14
 * @project youthhostelapp
 */
@Repository
public interface ServiceDetailRepository extends JpaRepository<GroupService, Integer> {
}
