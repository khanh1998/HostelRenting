package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.HostelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostelRoomRepository extends JpaRepository<HostelRoom, Integer> {
    HostelRoom getByRoomName(String hostelRoomName);
    Optional<HostelRoom> findByRoomIdAndHostelType_TypeId(Integer hostelRoomId, Integer hostelTypeId);
}
