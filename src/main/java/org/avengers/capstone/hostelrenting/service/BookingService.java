package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.BookingDTO;
import org.avengers.capstone.hostelrenting.model.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Optional<Booking> findAllBookingById(Integer id);
    List<BookingDTO> getAllBooking();
    List<BookingDTO> findAllBookingByVendorId(Integer vendorId);
    List<BookingDTO> findAllBookingByRenterId(Integer renterId);
    List<BookingDTO> findAllBookingByHostelTypeId(Integer hostelTypeId);
    Booking saveBooking(Booking booking);
    void removeBooking(Booking booking);
}
