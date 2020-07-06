package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.TypeStatus;

import java.util.List;

public interface TypeStatusService {
    TypeStatus findTypeStatusById(Integer id);
    List<TypeStatus> findAllTypeStatus();
    TypeStatus save(TypeStatus typeStatus);
    void deleteTypeStatus(Integer id);
}
