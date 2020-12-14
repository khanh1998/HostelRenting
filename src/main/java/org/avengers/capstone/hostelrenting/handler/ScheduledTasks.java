package org.avengers.capstone.hostelrenting.handler;

import net.bytebuddy.asm.Advice;
import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.notification.NotificationRequest;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.repository.HostelRequestRepository;
import org.avengers.capstone.hostelrenting.service.ContractService;
import org.avengers.capstone.hostelrenting.service.TypeService;
import org.avengers.capstone.hostelrenting.service.impl.FirebaseService;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author duattt on 11/10/20
 * @created 10/11/2020 - 08:35
 * @project youthhostelapp
 */
@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private DealRepository dealRepository;
    private HostelRequestRepository hostelRequestRepository;
    private BookingRepository bookingRepository;
    private ContractRepository contractRepository;
    private FirebaseService firebaseService;
    private Utilities utilities;

    @Autowired
    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }
    @Value("${system.domain}")
    private String systemDomain;

    @Value("${deal.expiring.day}")
    private int dealExpiringDay;

    @Value("${booking.expiring.day}")
    private int bookingExpiringDay;

    @Value("${contract.inactive.expiring.day}")
    private int inactiveContractExpiringDay;

    @Value("${system.append.time.hours}")
    private int systemCorrectionTime;

    @Value("${request.response.day}")
    private int requestResponseDay;

    @Autowired
    public void setFirebaseService(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Autowired
    public void setHostelRequestRepository(HostelRequestRepository hostelRequestRepository) {
        this.hostelRequestRepository = hostelRequestRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setDealRepository(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    /**
     * Run scheduled tasks every 00:00 each day
     */
    @Scheduled(cron = "0 0 0 * * ?") // execute every 00:00 of day
//    @Scheduled(cron = "*/5 * * ? * *") // execute every 5 seconds
    public void cleanUp() {
        cleanUpExpiredDeals();
        cleanUpExpiredBookings();
        cleanUpExpiredContracts();
        cleanUpExpiredRequests();
        sendRequestDueTime();
    }

    /**
     * Clean up all expired deal (deal without reference - STATUS!=DONE & STATUS!=CANCELLED based on updated and created time)
     */
    private void cleanUpExpiredDeals() {
        Long limitTime = calculateLimitTime(Calendar.DAY_OF_MONTH, dealExpiringDay);
        Collection<Deal> expiredDeals = dealRepository.findExpiredDealsByDayRange(limitTime);
        if (expiredDeals.isEmpty()) {
            logger.info("There is no expired DEAL");
            return;
        }
        expiredDeals.forEach(deal -> {
            deal.setStatus(Deal.STATUS.CANCELLED);
            dealRepository.save(deal);
            logger.info("DEAL with {id= " + deal.getDealId() + "} has been changed CANCELLED");
        });
    }

    /**
     * Clean up all expired booking (booking with INCOMING status and after meet_time 1 day)
     */
    private void cleanUpExpiredBookings() {
        // current time + 1 day + systemCorrectionTime
        Long limitTime = calculateLimitTime(Calendar.DAY_OF_MONTH, bookingExpiringDay);

        Collection<Booking> expiredBookings = bookingRepository.findExpiredBookingByDayRange(limitTime);
        if (expiredBookings.isEmpty()) {
            logger.info("There is no expired BOOKING");
            return;
        }
        expiredBookings.forEach(booking -> {
            booking.setStatus(Booking.STATUS.MISSING);
            bookingRepository.save(booking);
            logger.info("BOOKING with {id=" + booking.getBookingId() + "} has been changed to MISSING");
        });
    }

    /**
     * Clean up all expired contract (contract with INACTIVE status and start
     */
    private void cleanUpExpiredContracts() {
        Long limitTime = calculateLimitTime(Calendar.DAY_OF_MONTH, inactiveContractExpiringDay);
        Collection<Contract> expiredContracts = contractRepository.findExpiredInactiveContractByDayRange(limitTime);
        if (expiredContracts.isEmpty()) {
            logger.info("There is no expire CONTRACT");
            return;
        }
        expiredContracts.forEach(contract -> {
            contract.setStatus(Contract.STATUS.EXPIRED);
            contractRepository.save(contract);
            logger.info("CONTRACT with {id=" + contract.getContractId() + "} has been changed to EXPIRED");
        });
    }

    /**
     * Clean up all expired request (dueTime < currentTime)
     */
    private void cleanUpExpiredRequests() {
        Long limitTime = calculateLimitTime(Calendar.DAY_OF_MONTH, 0);
        Collection<HostelRequest> expiredRequests = hostelRequestRepository.findRequestsByLimitTime(limitTime);
        if (expiredRequests.isEmpty()) {
            logger.info("There is no expired HOSTEL REQUEST");
            return;
        }
        expiredRequests.forEach(request -> {
            request.setStatus(HostelRequest.STATUS.EXPIRED);
            logger.info(String.format("Request with {id=%s} has been changed to EXPIRED"), request.getRequestId());
            hostelRequestRepository.save(request);

        });
    }

    /**
     * send notification and email for request due time
     */
    private void sendRequestDueTime() {
        Long limitTime = calculateLimitTime(Calendar.DAY_OF_MONTH, requestResponseDay);
        Collection<HostelRequest> inTimeRequests = hostelRequestRepository.findRequestsByLimitTime(limitTime);
        if (inTimeRequests.isEmpty()) {
            logger.info("There is no in time REQUEST");
            return;
        }
        Map<String, String> data = new HashMap<>();
        data.put("title", "Danh sách phòng theo yêu cầu của bạn!");
        data.put("action", "REQUEST_HOSTEL_RESULT");
        inTimeRequests.forEach(request -> {
            firebaseService.sendPnsToDevice(NotificationRequest.builder().destination(request.getRenter().getFirebaseToken()).data(data).build());
            String resultUrl = "Danh sách các phòng dựa trên yêu cầu của bạn: " + systemDomain + "/request/"+request.getRequestId();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(request.getCreatedAt());
            utilities.sendMailWithEmbed(Constant.REQUEST_NOTIFICATION_SUBJECT , resultUrl, request.getRenter().getEmail());
            logger.info(String.format("Result of request with {id=%s} has been send to renter with {id=%s}",request.getRequestId(), request.getRenter().getUserId()));
        });
    }

    /**
     * calculate limit time for each task based on the timeRange and the systemCorrectionTime
     *
     * @param timeField Calendar constant
     * @param timeRange time range for limitation
     * @return milliseconds
     */
    private Long calculateLimitTime(int timeField, int timeRange) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(timeField, -timeRange);
        cal.add(Calendar.HOUR, systemCorrectionTime);
        return cal.getTimeInMillis();
    }

}
