package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelRoom;
import org.avengers.capstone.hostelrenting.repository.HostelRoomRepository;
import org.avengers.capstone.hostelrenting.service.HostelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelRoomServiceImpl implements HostelRoomService {
    private HostelRoomRepository hostelRoomRepository;

    @Autowired
    public void setHostelRoomRepository(HostelRoomRepository hostelRoomRepository) {
        this.hostelRoomRepository = hostelRoomRepository;
    }

    @Override
    public HostelRoom findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelRoom.class, "id", id.toString());
        }

        return hostelRoomRepository.getOne(id);
    }

    @Override
    public List<HostelRoom> findAll() {
        return hostelRoomRepository.findAll();
    }

    @Override
    public HostelRoom save(HostelRoom hostelRoom) {
        if (hostelRoomRepository.getByRoomName(hostelRoom.getRoomName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "hostel_room_name", hostelRoom.getRoomName()));
        }
        return hostelRoomRepository.save(hostelRoom);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelRoom.class, "id", id.toString());
        }

        hostelRoomRepository.deleteById(id);
    }

    @Override
    public HostelRoom findByIdAndHostelTypeId(Integer hostelRoomId, Integer hostelTypeId) {
        Optional<HostelRoom> hostelRoom = hostelRoomRepository.findByRoomIdAndHostelType_TypeId(hostelRoomId, hostelTypeId);
        if (hostelRoom.isEmpty()){
            throw new EntityNotFoundException(HostelRoom.class, "hostel_room_id", hostelRoomId.toString(), "hostel_type_id", hostelTypeId.toString());
        }
        return hostelRoom.get();
    }

    private boolean isNotFound(Integer id) {
        Optional<HostelRoom> hostelRoom = hostelRoomRepository.findById(id);
        return hostelRoom.isEmpty();
    }

}
