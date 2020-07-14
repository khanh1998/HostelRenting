package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.BookingStatus;
import org.avengers.capstone.hostelrenting.repository.BookingStatusRepository;
import org.avengers.capstone.hostelrenting.service.BookingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingStatusServiceImpl implements BookingStatusService {

    private BookingStatusRepository bookingStatusRepository;

    @Autowired
    public void setBookingStatusRepository(BookingStatusRepository bookingStatusRepository) {
        this.bookingStatusRepository = bookingStatusRepository;
    }

    @Override
    public BookingStatus save(BookingStatus bookingStatus) {
        if (bookingStatusRepository.getByStatusName(bookingStatus.getStatusName()) != null)
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "status_name", bookingStatus.getStatusName()));

        return bookingStatusRepository.save(bookingStatus);
    }

    @Override
    public BookingStatus findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(BookingStatus.class, "id", id.toString());
        }
        return bookingStatusRepository.getOne(id);
    }

    @Override
    public List<BookingStatus> findAll() {
        return bookingStatusRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(BookingStatus.class, "id", id.toString());
        }
        bookingStatusRepository.deleteById(id);
    }

    private boolean isNotFound(Integer id) {
        Optional<BookingStatus> bookingStatus = bookingStatusRepository.findById(id);
        return bookingStatus.isEmpty();
    }
}
