package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.repository.RoomRepository;
import org.avengers.capstone.hostelrenting.service.HostelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelRoomServiceImpl implements HostelRoomService {
    private RoomRepository roomRepository;

    @Autowired
    public void setHostelRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Room.class, "id", id.toString());
        }

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
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Room.class, "id", id.toString());
        }

        roomRepository.deleteById(id);
    }

    @Override
    public Room findByIdAndHostelTypeId(Integer hostelRoomId, Integer hostelTypeId) {
        Optional<Room> hostelRoom = roomRepository.findByRoomIdAndType_TypeId(hostelRoomId, hostelTypeId);
        if (hostelRoom.isEmpty()){
            throw new EntityNotFoundException(Room.class, "hostel_room_id", hostelRoomId.toString(), "hostel_type_id", hostelTypeId.toString());
        }
        return hostelRoom.get();
    }

    private boolean isNotFound(Integer id) {
        Optional<Room> hostelRoom = roomRepository.findById(id);
        return hostelRoom.isEmpty();
    }

}
