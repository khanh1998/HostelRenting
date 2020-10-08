package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room getByRoomName(String hostelRoomName);
    Optional<Room> findByRoomIdAndType_TypeId(Integer hostelRoomId, Integer hostelTypeId);
}
