package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackDTOCreate;
import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackDTOResponse;
import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackImageDTOCreate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.model.Feedback;
import org.avengers.capstone.hostelrenting.model.FeedbackImage;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

/**
 * @author duattt on 10/27/20
 * @created 27/10/2020 - 06:56
 * @project youthhostelapp
 */
@Controller
@RequestMapping("/api/v1")
public class FeedbackController {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
    private FeedbackService feedbackService;
    private TypeService typeService;
    private RenterService renterService;
    private ModelMapper modelMapper;


    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @PostMapping("/feedbacks")
    public ResponseEntity<?> createFeedback(@RequestBody @Valid FeedbackDTOCreate reqDTO) {
        logger.info("START - creating feedback");
        Feedback reqModel = modelMapper.map(reqDTO, Feedback.class);

        /* Prepare object for feedback model: renter, type, feedbackImage */
        Renter exRenter = renterService.findById(reqDTO.getRenterId());
        Type exType = typeService.findById(reqDTO.getTypeId());
        for (FeedbackImage feedbackImage : reqModel.getFeedbackImages()) {
            feedbackImage.setFeedback(reqModel);
        }

        /* Create and response feedback object */
        reqModel.setType(exType);
        reqModel.setRenter(exRenter);
        Feedback resModel = feedbackService.create(reqModel);
        FeedbackDTOResponse resDTO = modelMapper.map(resModel, FeedbackDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your feedback has been created successfully!");
        logger.info("END - creating feedback");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/feedbacks/{feedbackId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer feedbackId,
                                            @RequestBody @Valid FeedbackDTOUpdate reqDTO) {
        logger.info("START - updating feedback");
        Feedback exModel = feedbackService.findById(feedbackId);
        Feedback resModel = feedbackService.update(exModel, reqDTO);
        ApiSuccess<?> apiSuccess;
        FeedbackDTOResponse resDTO = modelMapper.map(resModel, FeedbackDTOResponse.class);
        apiSuccess = new ApiSuccess<>(resDTO, "Your feedback has been updated successfully!");

        logger.info("END - updating feedback");
        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @DeleteMapping("/feedbacks/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer feedbackId) {
        logger.info("START - deleting feedback");
        Feedback exModel = feedbackService.findById(feedbackId);
        feedbackService.deleteById(exModel.getFeedbackId());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>("Delete successfully!", true);
        logger.info("END - deleting feedback");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("types/{typeId}/feedbacks")
    public ResponseEntity<?> getFeedbacksByTypeId(@PathVariable Integer typeId,
                                                  @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                                  @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                  @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        logger.info("START - getting feedback");
        Collection<FeedbackDTOResponse> resDTOs = feedbackService.findByTypeId(typeId)
                .stream().map(feedback -> modelMapper.map(feedback, FeedbackDTOResponse.class))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Feedback has been retrieved successfully!");
        logger.info("END - getting feedback");
        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
