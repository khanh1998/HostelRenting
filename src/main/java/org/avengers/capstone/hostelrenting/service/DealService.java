package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Deal;

public interface DealService {
    Deal findById(Integer id);
    Deal changeStatus(Integer id, Deal.Status status);
    Deal save(Deal deal);
}
