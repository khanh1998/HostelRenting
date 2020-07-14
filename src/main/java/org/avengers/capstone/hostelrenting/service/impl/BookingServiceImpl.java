package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking findById(Integer id) {
        if (isNotFound(id))
            throw new EntityNotFoundException(Booking.class, "id", id.toString());

        return bookingRepository.getOne(id);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking save(Booking booking) {
        if (booking.getQrCode() == null){
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            booking.setQrCode(String.format("%06d", number));
        }
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Booking.class, "id", id.toString());
        }
        bookingRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.isEmpty();
    }
}
