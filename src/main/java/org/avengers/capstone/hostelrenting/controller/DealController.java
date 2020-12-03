package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOResponse;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOCreate;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponseShort;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Deal;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.GroupService;
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
    private GroupService groupService;
    private DealService dealService;

    @Autowired
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
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
    public ResponseEntity<?> createDeal(@RequestBody @Valid DealDTOCreate reqDTO) {
        Deal resModel = dealService.create(reqDTO);
        DealDTOResponse resDTO = modelMapper.map(resModel, DealDTOResponse.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO,
                "Your deal has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

//    @PutMapping("/deals/{dealId}")
//    public ResponseEntity<?> updateDealById(@PathVariable Integer dealId,
//                                            @Valid @RequestBody DealDTOCreate reqDTO) {
//
//        String resMsg = "Your deal has been updated";
//        DealDTOCreate resDTO = null;
//        reqDTO.setDealId(dealId);
//        Deal updatedModel = dealService.update(reqDTO);
//
//        if (updatedModel == null)
//            resMsg = "Your booking is up to date";
//        else
//            resDTO = modelMapper.map(updatedModel, DealDTOCreate.class);
//
//        // Response entity
//        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);
//
//        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
//    }

    @GetMapping("/deals/{dealId}")
    public ResponseEntity<?> getDealById(@PathVariable Integer dealId) throws EntityNotFoundException {
        Deal resModel = dealService.findById(dealId);
        DealDTOResponse resDTO = modelMapper.map(resModel, DealDTOResponse.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your deal has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/deals")
    public ResponseEntity<?> getDealsByRenterId(@PathVariable Long renterId) throws EntityNotFoundException {
        List<DealDTOResponse> resDeals = dealService.findByRenterId(renterId)
                .stream()
                .map(deal -> modelMapper.map(deal, DealDTOResponse.class))
                .collect(Collectors.toList());

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDeals,
                "Your deal(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/deals")
    public ResponseEntity<?> getDealsByVendorId(@PathVariable Long vendorId) throws EntityNotFoundException {
        List<DealDTOResponse> resDeals = dealService.findByVendorId(vendorId)
                .stream()
                .map(deal -> modelMapper.map(deal, DealDTOResponse.class))
                .collect(Collectors.toList());

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDeals,
                "Your deal(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

//    /**
//     * Get corresponding {@link Group}
//     *
//     * @param deals list of {@link org.avengers.capstone.hostelrenting.model.Booking} need to fill {@link Group}
//     * @return list of {@link org.avengers.capstone.hostelrenting.model.Booking} with corresponding {@link Group}
//     */
//    private List<DealDTOResponse> getGroupForDeal(List<DealDTOResponse> deals) {
//        deals.forEach(deal -> {
//            Group existedGroup = groupService.findById(deal.getType().getGroupId());
//            deal.setGroup(modelMapper.map(existedGroup, GroupDTOResponseShort.class));
//        });
//        return deals;
//    }
}
