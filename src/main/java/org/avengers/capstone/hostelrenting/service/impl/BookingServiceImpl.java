package org.avengers.capstone.hostelrenting.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationContent;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequest;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.repository.RoomRepository;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BookingServiceImpl implements BookingService {
    @Value("${deal.expiring.day}")
    private Long dealExpiringTime;

    private BookingRepository bookingRepository;
    private RoomRepository roomRepository;

    private DealService dealService;
    private ModelMapper modelMapper;
    private VendorService vendorService;
    private RenterService renterService;
    private FirebaseService firebaseService;

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Booking> model = bookingRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Booking.class, "id", id.toString());
    }

    @Override
    public Booking findById(Integer id) {
        checkExist(id);
        return bookingRepository.getOne(id);

    }

    @Override
    public Booking create(Booking reqModel) {
        handlePreCreate(reqModel);

        // check out of room
        if (roomRepository.countByType_TypeIdAndIsAvailableIsTrue(reqModel.getType().getTypeId()) == 0)
            throw new GenericException(Type.class, "is out of room",
                    "typeId", String.valueOf(reqModel.getType().getTypeId()));

        // Update deal status if exist (booking created means deal is done)
        Integer dealId = reqModel.getDealId();
        if (dealId != null) {
            dealService.checkActive(dealId);
            dealService.changeStatus(dealId, Deal.STATUS.DONE);
        }

        Booking resModel = bookingRepository.save(reqModel);

        return resModel;
    }


    @Override
    public Booking confirm(Booking exModel, BookingDTOUpdate reqDTO) {
        if (exModel.getStatus() == Booking.STATUS.DONE) {
            throw new GenericException(Booking.class, "cannot be updated", "bookingId", String.valueOf(exModel.getBookingId()), "status", exModel.getStatus().toString());
        }
        // Update status
        if (exModel.getQrCode().equals(reqDTO.getQrCode())) {
            modelMapper.map(reqDTO, exModel);
            Booking resModel = bookingRepository.save(exModel);
            return resModel;
        }
        throw new GenericException(Booking.class, "qrCode not matched", "bookingId", String.valueOf(exModel.getBookingId()), "qrCode", exModel.getQrCode().toString());
        // Update meetTime
        //TODO: Implement here
    }

    /**
     * Get list of bookings by given renter id
     *
     * @param renterId given renter id to get booking
     * @return list of booking models
     */
    @Override
    public List<Booking> findByRenterId(Long renterId) {
        renterService.checkExist(renterId);

        return renterService.findById(renterId).getBookings();
    }

    /**
     * Get list of bookings by given vendor id
     *
     * @param vendorId given vendor id to get booking
     * @return list of booking models
     */
    @Override
    public List<Booking> findByVendorId(Long vendorId) {
        vendorService.checkExist(vendorId);

        return vendorService.findById(vendorId).getBookings();
    }

    @Override
    public boolean cancelBookings(Collection<Integer> bookingIds) {
        bookingIds.forEach(id -> {
            Booking model = findById(id);
            bookingRepository.save(model.toBuilder()
                    .status(Booking.STATUS.CANCELLED)
                    .updatedAt(System.currentTimeMillis())
                    .build());
        });
        return true;
    }
    

    private void handlePreCreate(Booking model) {
        // 1 renter only have 1 incoming booking for 1 type
        Optional<Booking> tempBooking = bookingRepository.findFirstByRenter_UserIdAndType_TypeIdAndStatusOrderByCreatedAtDesc(model.getRenter().getUserId(),
                model.getType().getTypeId(), Booking.STATUS.INCOMING);
        if (tempBooking.isPresent()) {
            throw new GenericException(Booking.class, "has already existed with ",
                    "bookingId", String.valueOf(tempBooking.get().getBookingId()),
                    "renterId", String.valueOf(tempBooking.get().getRenter().getUserId()),
                    "typeId", String.valueOf(tempBooking.get().getType().getTypeId()));
        }

        // only create booking at least 3 hours from now
        Long remainBookingTime = TimeUnit.MILLISECONDS.toHours(model.getMeetTime() - System.currentTimeMillis());
        if (remainBookingTime < 3) {
            throw new GenericException(Booking.class, "remaining time should be at least 3 hours",
                    "remainingTime", remainBookingTime + "hours");
        }

        // check valid deal or not (3 day expiring)
        if (model.getDealId()!= null) {
            Deal exDeal = dealService.findById(model.getDealId());
            Long remainDealTime = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - exDeal.getCreatedAt());
            if (remainDealTime >= dealExpiringTime) {
                model.setDealId(null);
            }
        }
    }
}
