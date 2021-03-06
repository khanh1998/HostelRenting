package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.repository.RoomRepository;
import org.avengers.capstone.hostelrenting.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public void setHostelRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findById(Integer id) {
        checkExist(id);

        return roomRepository.getOne(id);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        if (roomRepository.getByRoomName(room.getRoomName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "hostel_room_name", room.getRoomName()));
        }
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(Integer id) {
        checkExist(id);

        roomRepository.deleteById(id);
    }

    @Override
    public Boolean checkAvailableById(Integer id) {
        return findById(id).isAvailable();
    }

    @Override
    public Room updateStatus(Integer id, boolean isAvailable) {
        return roomRepository.save(findById(id).toBuilder().isAvailable(isAvailable).build());
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Room> model = roomRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Room.class, "id", id.toString());
    }

}
