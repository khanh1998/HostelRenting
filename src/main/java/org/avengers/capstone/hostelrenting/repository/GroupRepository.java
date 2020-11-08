package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
//    @Query
//    Integer getGroupIdByRoomId(Integer roomId);
}
