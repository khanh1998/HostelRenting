package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Room;

import java.util.List;

public interface HostelRoomService {
    Room findById(Integer id);
    List<Room> findAll();
    Room save(Room room);
    void deleteById(Integer id);

    Room findByIdAndHostelTypeId(Integer hostelRoomId, Integer hostelTypeId);
}
