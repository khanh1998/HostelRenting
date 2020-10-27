package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Contract;
import org.avengers.capstone.hostelrenting.model.Feedback;
import org.avengers.capstone.hostelrenting.repository.BookingRepository;
import org.avengers.capstone.hostelrenting.repository.ContractRepository;
import org.avengers.capstone.hostelrenting.repository.FeedbackRepository;
import org.avengers.capstone.hostelrenting.service.BookingService;
import org.avengers.capstone.hostelrenting.service.ContractService;
import org.avengers.capstone.hostelrenting.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author duattt on 10/26/20
 * @created 26/10/2020 - 13:36
 * @project youthhostelapp
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
    private FeedbackRepository feedbackRepository;
    private BookingRepository bookingRepository;
    private BookingService bookingService;
    private ContractService contractService;
    private ContractRepository contractRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setFeedbackRepository(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Feedback> model = feedbackRepository.findById(id);
        if (model.isEmpty() || model.get().isDeleted())
            throw new EntityNotFoundException(Feedback.class, "id", id.toString());
    }

    @Override
    public Feedback findById(Integer feedbackId) {
        checkExist(feedbackId);
        return feedbackRepository.getOne(feedbackId);
    }

    @Override
    public Feedback create(Feedback reqModel) {
        reqModel = handleFeedbackObj(reqModel);
        return feedbackRepository.save(reqModel);
    }

    @Override
    public Feedback update(Feedback exModel, FeedbackDTOUpdate reqDTO) {
        modelMapper.map(reqDTO, exModel);
        exModel = handleFeedbackObj(exModel);
        return feedbackRepository.save(exModel);
    }

    @Override
    public void deleteById(Integer feedbackId) {
        checkExist(feedbackId);
        Feedback exModel = findById(feedbackId);
        exModel.setDeleted(true);
        feedbackRepository.save(exModel);
    }

    @Override
    public Collection<Feedback> findByTypeId(Integer typeId) {
        return feedbackRepository.findByType_TypeIdAndIsDeletedIsFalse(typeId);
    }


    private Feedback handleFeedbackObj(Feedback model){
        Optional<Booking> exBooking = bookingRepository.findFirstByRenter_UserIdAndType_TypeIdAndStatusOrderByCreatedAtDesc(model.getRenter().getUserId(),
                model.getType().getTypeId(),
                Booking.STATUS.DONE);
        exBooking.ifPresent(model::setBooking);

        Optional<Contract> exContract = contractRepository.findFirstByRenter_UserIdAndRoom_Type_TypeIdAndStatusOrStatusOrderByCreatedAt(model.getRenter().getUserId(),
                model.getType().getTypeId(),
                Contract.STATUS.ACTIVATED,
                Contract.STATUS.EXPIRED);
        exContract.ifPresent(model::setContract);

        return model;
    }
}