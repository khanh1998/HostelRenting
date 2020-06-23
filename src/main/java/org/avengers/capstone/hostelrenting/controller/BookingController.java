package capstone.tdhostel.controller;

import capstone.tdhostel.dto.BookingDTO;
import capstone.tdhostel.dto.BookingNoIdDTO;
import capstone.tdhostel.dto.ScheduleDTO;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.Booking;
import capstone.tdhostel.model.Schedule;
import capstone.tdhostel.model.Vendor;
import capstone.tdhostel.service.BookingService;
import capstone.tdhostel.service.HostelTypeService;
import capstone.tdhostel.service.RenterService;
import capstone.tdhostel.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private RenterService renterService;

    @Autowired
    private HostelTypeService hostelTypeService;

    @GetMapping("/bookings")
    public List<BookingDTO> getAllBooking() {
        return bookingService.getAllBooking();
    }

    @GetMapping("/bookings/{id}")
    public Booking getBookingById(@PathVariable int id) {
        return bookingService.findAllBookingById(id).get();
    }

    @GetMapping("/bookings/vendor/{vendorId}")
    public List<BookingDTO> getAllBookingByVendorId(@PathVariable int vendorId) {
        return bookingService.findAllBookingByVendorId(vendorId);
    }

    @GetMapping("/bookings/renter/{renterId}")
    public List<BookingDTO> getAllBookingByRenterId(@PathVariable int renterId) {
        return bookingService.findAllBookingByRenterId(renterId);
    }

    @GetMapping("/bookings/type/{hostelTypeId}")
    public List<BookingDTO> getAllBookingByHostelTypeId(@PathVariable int hostelTypeId) {
        return bookingService.findAllBookingByHostelTypeId(hostelTypeId);
    }

    @PostMapping("/bookings")
    public  ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setDealId(bookingDTO.getDealId());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setStatus(bookingDTO.getStatus());
        booking.setVendor(vendorService.findVendorByVendorId(bookingDTO.getVendorId()));
        booking.setRenter(renterService.findRenterByRenterId(bookingDTO.getRenterId()));
        booking.setHostelType(hostelTypeService.findHostelTypeByHostelTypeId(bookingDTO.getHostelTypeId()));
        bookingService.saveBooking(booking);
        return  ResponseEntity.ok(bookingDTO);
    }

    @PutMapping("/bookings/{bookingId}")
    public BookingNoIdDTO updateBooking(@PathVariable int bookingId, @RequestBody BookingNoIdDTO bookingRequest) {
        return bookingService.findAllBookingById(bookingId).map(booking -> {
            booking.setDealId(bookingRequest.getDealId());
            booking.setStartTime(bookingRequest.getStartTime());
            booking.setEndTime(bookingRequest.getEndTime());
            booking.setStatus(bookingRequest.getStatus());
            booking.setVendor(vendorService.findVendorByVendorId(bookingRequest.getVendorId()));
            booking.setRenter(renterService.findRenterByRenterId(bookingRequest.getRenterId()));
            booking.setHostelType(hostelTypeService.findHostelTypeByHostelTypeId(bookingRequest.getHostelTypeId()));
            bookingService.saveBooking(booking);
            return bookingRequest;
        }).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<?> removeBooking(@PathVariable int bookingId) {
        return bookingService.findAllBookingById(bookingId).map(booking -> {
            bookingService.removeBooking(booking);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
    }
}
