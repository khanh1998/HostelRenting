package org.avengers.capstone.hostelrenting.repository;

import org.avengers.capstone.hostelrenting.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Room getByRoomName(String hostelRoomName);
    Optional<Room> findByRoomIdAndType_TypeId(Integer hostelRoomId, Integer hostelTypeId);
    int countByType_TypeIdAndIsAvailableIsTrue(Integer typeId);
    @Query("select case when count (r) > 0 then true else false end from Room r where r.type.group.vendor.userId= :vendorId and r.roomId= :roomId")
    boolean IsExistByVendorIdAndRoomId(Long vendorId, Integer roomId);

}
