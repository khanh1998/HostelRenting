package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.BookingDTO;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Optional<Booking> findAllBookingById(Integer id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<BookingDTO> getAllBooking() {
        List<Booking> bookingList = bookingRepository.findAll();
        List<BookingDTO> bookingDTOList = bookingList.stream().map(item -> {
            BookingDTO dto = new BookingDTO();
            dto.setBookingId(item.getBookingId());
            dto.setDealId(item.getDealId());
            dto.setStartTime(item.getStartTime());
            dto.setEndTime(item.getEndTime());
            dto.setVendorId(item.getVendor().getVendorId());
            dto.setRenterId(item.getRenter().getRenterId());
            return dto;
        }).collect(Collectors.toList());
        return bookingDTOList;
    }

    @Override
    public List<BookingDTO> findAllBookingByVendorId(Integer vendorId) {
        List<Booking> bookingList = bookingRepository.findAllBookingByVendorId(vendorId);
        List<BookingDTO> bookingDTOList = bookingList.stream().map(item -> {
            BookingDTO dto = new BookingDTO();
            dto.setBookingId(item.getBookingId());
            dto.setDealId(item.getDealId());
            dto.setStartTime(item.getStartTime());
            dto.setEndTime(item.getEndTime());
            dto.setVendorId(item.getVendor().getVendorId());
            dto.setRenterId(item.getRenter().getRenterId());
            return dto;
        }).collect(Collectors.toList());
        return bookingDTOList;
    }

    @Override
    public List<BookingDTO> findAllBookingByRenterId(Integer renterId) {
        List<Booking> bookingList = bookingRepository.findAllBookingByRenterId(renterId);
        List<BookingDTO> bookingDTOList = bookingList.stream().map(item -> {
            BookingDTO dto = new BookingDTO();
            dto.setBookingId(item.getBookingId());
            dto.setDealId(item.getDealId());
            dto.setStartTime(item.getStartTime());
            dto.setEndTime(item.getEndTime());
            dto.setVendorId(item.getVendor().getVendorId());
            dto.setRenterId(item.getRenter().getRenterId());
            return dto;
        }).collect(Collectors.toList());
        return bookingDTOList;
    }

    @Override
    public List<BookingDTO> findAllBookingByHostelTypeId(Integer hostelTypeId) {
        List<Booking> bookingList = bookingRepository.findAllBookingByHostelTypeId(hostelTypeId);
        List<BookingDTO> bookingDTOList = bookingList.stream().map(item -> {
            BookingDTO dto = new BookingDTO();
            dto.setBookingId(item.getBookingId());
            dto.setDealId(item.getDealId());
            dto.setStartTime(item.getStartTime());
            dto.setEndTime(item.getEndTime());
            dto.setVendorId(item.getVendor().getVendorId());
            dto.setRenterId(item.getRenter().getRenterId());
            return dto;
        }).collect(Collectors.toList());
        return bookingDTOList;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void removeBooking(Booking booking) {
        bookingRepository.delete(booking);
    }
}
