package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.model.Booking;

import java.util.List;

public interface BookingService {
    void checkActive(Integer id);
    Booking findById(Integer id);
    Booking create(BookingDTOShort reqDTO);
    Booking update(BookingDTOShort reqDTO);
    void delete(Integer id);
    List<Booking> findByRenterId(Long renterId);
    List<Booking> findByVendorId(Long vendorId);

    Booking changeStatus(Integer id, Booking.STATUS status);
}

