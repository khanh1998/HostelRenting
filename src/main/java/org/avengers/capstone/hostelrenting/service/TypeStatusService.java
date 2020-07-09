package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.TypeStatus;

import java.util.List;

public interface TypeStatusService {
    TypeStatus save(TypeStatus typeStatus);
    TypeStatus findById(Integer id);
    List<TypeStatus> findAll();
    void delete(Integer id);
}
