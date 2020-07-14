package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.BookingStatus;
import org.avengers.capstone.hostelrenting.model.TypeStatus;

import java.util.List;

public interface BookingStatusService {
    BookingStatus save(BookingStatus bookingStatus);
    BookingStatus findById(Integer id);
    List<BookingStatus> findAll();
    void deleteById(Integer id);
}
