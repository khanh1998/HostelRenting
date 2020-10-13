package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
