package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOCreate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.ServiceDetail;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class HostelGroupController {
    private static final Logger logger = LoggerFactory.getLogger(HostelGroupController.class);

    private HostelGroupService hostelGroupService;

    private StreetWardService streetWardService;

    private VendorService vendorService;

    private ModelMapper modelMapper;

    @Autowired
    public void setStreetWardService(StreetWardService streetWardService) {
        this.streetWardService = streetWardService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Create list of hostel groups from request body
     *
     * @param reqDTOs list of {@link GroupDTOCreate} obj from request
     * @return list of {@link GroupDTOCreate} obj has been created
     * @throws EntityNotFoundException when some object not found
     */
    @PostMapping("/groups")
    public ResponseEntity<?> createHostelGroup(@Valid @RequestBody List<GroupDTOCreate> reqDTOs) throws EntityNotFoundException {
        logger.info("START - creating group(s)");
        // get necessary for model: vendor, address, services
        List<GroupDTOCreate> resDTOs = new ArrayList<>();
        reqDTOs.forEach(reqDTO -> {
            HostelGroup hostelGroupModel = modelMapper.map(reqDTO, HostelGroup.class);
            hostelGroupModel.setVendor(vendorService.findById(reqDTO.getVendorId()));
            hostelGroupModel.setAddress(streetWardService.findByStreetIdAndWardId(reqDTO.getAddressFull().getStreetId(), reqDTO.getAddressFull().getWardId()));
            Collection<ServiceDetail> serviceDetails = reqDTO.getServices()
                    .stream()
                    .map(dto -> {
                        dto.setCreatedAt(System.currentTimeMillis());
                        ServiceDetail serviceDetail = modelMapper.map(dto, ServiceDetail.class);
                        serviceDetail.setHGroup(hostelGroupModel);
                        return serviceDetail;
                    })
                    .collect(Collectors.toList());
            hostelGroupModel.setServiceDetails(serviceDetails);
            HostelGroup resModel = hostelGroupService.create(hostelGroupModel);

            // log created group
            logger.info("CREATED Group with id: " + resModel.getGroupId());

            GroupDTOCreate resDTO = modelMapper.map(resModel, GroupDTOCreate.class);
            resDTOs.add(resDTO);
        });

        // log success create groups
        logger.info("SUCCESSFUL - Create group(s)");
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    /**
     * Get group object by the given id
     *
     * @param groupId specific id of group
     * @return a group with given id
     */
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable Integer groupId) {
        logger.info("START - Get group with id: " + groupId);
        HostelGroup resModel = hostelGroupService.findById(groupId);
        GroupDTOResponse resDTO = modelMapper.map(resModel, GroupDTOResponse.class);
        if (resModel != null) {
            logger.info("SUCCESSFUL - Get group by id");
        }

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    /**
     * Get group info by vendor id
     *
     * @param vendorId id of vendor
     * @return list of group belong to vendor
     * @throws EntityNotFoundException when object is not found
     */
    @GetMapping("/vendors/{vendorId}/groups")
    public ResponseEntity<?> getGroupsByVendorId(@PathVariable Long vendorId) throws EntityNotFoundException {
        //log start
        logger.info("START - Get group by vendor with id: " + vendorId);
        Vendor existedModel = vendorService.findById(vendorId);
        List<GroupDTOResponse> resDTOs = existedModel.getHostelGroups()
                .stream()
                .map(hostelGroup -> {
                    logger.info("RETRIEVED - groupId: " + hostelGroup.getGroupId());
                    return modelMapper.map(hostelGroup, GroupDTOResponse.class);
                })
                .collect(Collectors.toList());

        logger.info("SUCCESSFUL - Get group by vendorId");
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
