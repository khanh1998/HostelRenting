package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("select gr.groupId from Room r, Type t, Group gr where r.type.typeId = t.typeId and t.group.groupId = gr.groupId and r.roomId= :roomId")
    Integer getGroupIdByRoomId(Integer roomId);
}
