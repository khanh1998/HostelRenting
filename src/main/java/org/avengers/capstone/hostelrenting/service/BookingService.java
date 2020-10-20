package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOCreate;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Booking;

import java.util.Collection;
import java.util.List;

public interface BookingService {
    void checkExist(Integer id);
    Booking findById(Integer id);
    Booking create(Booking reqModel);
    Booking confirm(Booking exModel, BookingDTOUpdate reqDTO);
    List<Booking> findByRenterId(Long renterId);
    List<Booking> findByVendorId(Long vendorId);
    boolean cancelBookings(Collection<Integer> bookingIds);
}

