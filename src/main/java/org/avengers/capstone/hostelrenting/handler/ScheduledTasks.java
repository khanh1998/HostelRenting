package org.avengers.capstone.hostelrenting.handler;

import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.avengers.capstone.hostelrenting.repository.DealRepository;
import org.avengers.capstone.hostelrenting.repository.HostelRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public void setHostelRequestRepository(HostelRequestRepository hostelRequestRepository) {
        this.hostelRequestRepository = hostelRequestRepository;
    }

    @Autowired
    public void setDealRepository(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "*/5 * * ? * *")
    public void cleanUp() {
        cleanUpExpiredDeals();
        cleanUpExpiredRequests();
    }

    private void cleanUpExpiredDeals() {
        Collection<Deal> expiredDeals = dealRepository.findExpiredDealsByDayRange(String.valueOf(3));
        if (expiredDeals.isEmpty()) {
            logger.info("There is no expired deal");
            return;
        }
        expiredDeals.forEach(deal -> {
            deal.setStatus(Deal.STATUS.CANCELLED);
            logger.info("Deal with {id= " + deal.getDealId() + "} has been CANCELLED");
            dealRepository.save(deal);
        });
    }

    private void cleanUpExpiredRequests() {
        Collection<HostelRequest> expiredRequests = hostelRequestRepository.findExpiredRequests();
        if (expiredRequests.isEmpty()) {
            logger.info("There is no expired request");
            return;
        }
        expiredRequests.forEach(request -> {
            request.setStatus(HostelRequest.STATUS.EXPIRED);
            logger.info(String.format("Request with {id=%s} has been changed to EXPIRED"));
            hostelRequestRepository.save(request);

        });
    }

}
