package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.repository.RoomRepository;
import org.avengers.capstone.hostelrenting.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        if (roomRepository.getByRoomNameAndType_TypeId(room.getRoomName(), room.getType().getTypeId()) != null) {
            throw new GenericException(Room.class, "is existed with ", "id", room.getRoomName());
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
    public Room getExistedRoomInList(Collection<Room> rooms) {
        for (Room room : rooms) {
            Room exRoom = roomRepository.getByRoomNameAndType_TypeId(room.getRoomName(), room.getType().getTypeId());
            if (exRoom != null)
                return exRoom;
        }
        return null;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Room> model = roomRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Room.class, "id", id.toString());
    }

}
