package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Room;

import java.util.List;

public interface RoomService {
    void checkExist(Integer id);
    Room findById(Integer id);
    List<Room> findAll();
    Room save(Room room);
    void deleteById(Integer id);

    Boolean checkAvailableById(Integer id);
    Room updateStatus(Integer id, boolean isAvailable);

}
