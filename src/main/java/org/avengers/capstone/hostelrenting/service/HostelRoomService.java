package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelRoom;

import java.util.List;

public interface HostelRoomService {
    HostelRoom findById(Integer id);
    List<HostelRoom> findAll();
    HostelRoom save(HostelRoom hostelRoom);
    void deleteById(Integer id);

    HostelRoom findByIdAndHostelTypeId(Integer hostelRoomId, Integer hostelTypeId);
}
