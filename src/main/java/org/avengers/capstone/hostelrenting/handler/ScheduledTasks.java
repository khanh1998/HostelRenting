package org.avengers.capstone.hostelrenting.handler;

import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.repository.HostelRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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

    @Value("${deal.expiring.day}")
    private String dealExpiringDay;

    @Value("${booking.expiring.day}")
    private String bookingExpiringDay;

    @Value("${contract.inactive.expiring.day}")
    private String inactiveContractExpiringDay;

//    @Value("${deal.delete.day}")
//    private String dealDeleteDay;



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
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "*/5 * * ? * *")
    public void cleanUp() {
        cleanUpExpiredDeals();
        cleanUpExpiredBookings();
        cleanUpExpiredContracts();
        cleanUpExpiredRequests();
    }

    /**
     * Clean up all expired deal (deal without reference - STATUS!=DONE & STATUS!=CANCELLED based on updated and created time)
     */
    private void cleanUpExpiredDeals() {
        Collection<Deal> expiredDeals = dealRepository.findExpiredDealsByDayRange(dealExpiringDay);
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
        Collection<Booking> expiredBookings = bookingRepository.findExpiredBookingByDayRange(bookingExpiringDay);
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
        Collection<Contract> expiredContracts = contractRepository.findExpiredInactiveContractByDayRange(inactiveContractExpiringDay);
        if (expiredContracts.isEmpty()){
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
     *
     */
    private void cleanUpExpiredRequests() {
        Collection<HostelRequest> expiredRequests = hostelRequestRepository.findExpiredRequests();
        if (expiredRequests.isEmpty()) {
            logger.info("There is no expired HOSTEL REQUEST");
            return;
        }
        expiredRequests.forEach(request -> {
            request.setStatus(HostelRequest.STATUS.EXPIRED);
            logger.info("Request with {id=%s} has been changed to EXPIRED");
            hostelRequestRepository.save(request);

        });
    }

}
