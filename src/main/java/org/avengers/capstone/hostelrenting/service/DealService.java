package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.Deal;

import java.util.List;

public interface DealService {
    Deal findById(Integer id);
    List<Deal> findAll();
    Deal save(Deal deal);
    void deleteById(Integer id);
}
