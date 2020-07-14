package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;

import java.util.List;

public interface BookingService {
    Booking findById(Integer id);
    List<Booking> findAll();
    Booking save(Booking booking);
    void deleteById(Integer id);
}

