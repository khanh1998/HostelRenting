package capstone.tdhostel.service;

import capstone.tdhostel.dto.BookingDTO;
import capstone.tdhostel.model.Booking;

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
