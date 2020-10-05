package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOFull;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class DealController {

    private ModelMapper modelMapper;
    private HostelGroupService hostelGroupService;
    private DealService dealService;

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("/deals")
    public ResponseEntity<?> create(@RequestBody @Valid DealDTOShort reqDTO) {
        Deal resModel = dealService.create(reqDTO);
        DealDTOShort resDTO = modelMapper.map(resModel, DealDTOShort.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO,
                "Your deal has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/deals/{dealId}")
    public ResponseEntity<?> updateDeal(@PathVariable Integer dealId,
                                        @Valid @RequestBody DealDTOShort reqDTO) {

        String resMsg = "Your deal has been updated";
        DealDTOShort resDTO = null;
        reqDTO.setDealId(dealId);
        Deal updatedModel = dealService.update(reqDTO);

        if (updatedModel == null)
            resMsg = "Your booking is up to date";
        else
            resDTO = modelMapper.map(updatedModel, DealDTOShort.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/deals/{dealId}")
    public ResponseEntity<?> getDealById(@PathVariable Integer dealId) throws EntityNotFoundException {
        Deal resModel = dealService.findById(dealId);
        DealDTOFull resDTO = modelMapper.map(resModel, DealDTOFull.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your deal has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/deals")
    public ResponseEntity<?> getDealsByRenterId(@PathVariable Long renterId) throws EntityNotFoundException {
        List<DealDTOFull> resDeals = dealService.findByRenterId(renterId)
                .stream()
                .map(deal -> modelMapper.map(deal, DealDTOFull.class))
                .collect(Collectors.toList());

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(getGroupForDeal(resDeals),
                "Your deal(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/deals")
    public ResponseEntity<?> getDealsByVendorId(@PathVariable Long vendorId) throws EntityNotFoundException {
        List<DealDTOFull> resDeals = dealService.findByVendorId(vendorId)
                .stream()
                .map(deal -> modelMapper.map(deal, DealDTOFull.class))
                .collect(Collectors.toList());

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(getGroupForDeal(resDeals),
                "Your deal(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    /**
     * Get corresponding {@link HostelGroup}
     *
     * @param deals list of {@link org.avengers.capstone.hostelrenting.model.Booking} need to fill {@link HostelGroup}
     * @return list of {@link org.avengers.capstone.hostelrenting.model.Booking} with corresponding {@link HostelGroup}
     */
    private List<DealDTOFull> getGroupForDeal(List<DealDTOFull> deals) {
        deals.forEach(deal -> {
            HostelGroup existedGroup = hostelGroupService.findById(deal.getType().getGroupId());
            deal.setGroup(modelMapper.map(existedGroup, GroupDTOResponse.class));
        });
        return deals;
    }
}
