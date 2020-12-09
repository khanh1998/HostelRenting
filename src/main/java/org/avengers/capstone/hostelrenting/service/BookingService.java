package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOCreate;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Booking;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {
    void checkExist(Integer id);

    Booking findById(Integer id);

    Booking create(Booking reqModel);

    Booking confirm(Booking exModel, BookingDTOUpdate reqDTO);

    List<Booking> findByRenterId(UUID renterId);

    List<Booking> findByVendorId(UUID vendorId);

    boolean cancelBookings(Collection<Integer> bookingIds);

}
