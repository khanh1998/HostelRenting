package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOCreate;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@Validated
@RestController
@RequestMapping("/api/v1")
public class GroupController {
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    private GroupService groupService;

    private StreetWardService streetWardService;

    private VendorService vendorService;

    private RegulationService regulationService;

    private ServiceService serviceService;

    private ScheduleService scheduleService;
    private StreetService streetService;

    private ModelMapper modelMapper;

    @Autowired
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setRegulationService(RegulationService regulationService) {
        this.regulationService = regulationService;
    }

    @Autowired
    public void setStreetWardService(StreetWardService streetWardService) {
        this.streetWardService = streetWardService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
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
    public ResponseEntity<?> createGroup(@RequestBody
                                         @NotEmpty(message = "Input group list cannot be empty.")
                                                 List<@Valid GroupDTOCreate> reqDTOs) throws EntityNotFoundException {
        logger.info("START - creating group(s)");
        // get necessary for model: vendor, address, services
        List<GroupDTOResponse> resDTOs = new ArrayList<>();
        reqDTOs.forEach(reqDTO -> {
            Group reqModel = modelMapper.map(reqDTO, Group.class);
            // set vendor object
            Vendor vendor = vendorService.findById(reqDTO.getVendorId());
            reqModel.setVendor(vendor);
            // set address object
            Street street = Street.builder()
                    .streetName(reqDTO.getAddressFull().getStreetName())
                    .build();
            // create street and save streetward if not exist
            StreetWard address = streetWardService.findByStreetIdAndWardId(streetService.createIfNotExist(street).getStreetId(), reqDTO.getAddressFull().getWardId());
            reqModel.setAddress(address);
            // set manager info
            if (reqModel.getManagerName() == null){
                reqModel.setManagerName(vendor.getUsername());
            }
            if (reqModel.getManagerPhone() == null){
                reqModel.setManagerPhone(vendor.getPhone());
            }

            /* set services */
            if (reqDTO.getServices() != null) {
                Collection<org.avengers.capstone.hostelrenting.model.GroupService> groupServices = reqDTO.getServices()
                        .stream()
                        .map(dto -> {
                            org.avengers.capstone.hostelrenting.model.GroupService groupService = org.avengers.capstone.hostelrenting.model.GroupService.builder()
                                    .createdAt(System.currentTimeMillis())
                                    .group(reqModel)
                                    .price(dto.getPrice())
                                    .userUnit(dto.getUserUnit())
                                    .service(serviceService.findById(dto.getServiceId()))
                                    .isActive(true)
                                    .isRequired(true)
                                    .build();
                            return groupService;
                        })
                        .collect(Collectors.toList());
                reqModel.setGroupServices(groupServices);
            }

            /* set regulation */
            if (reqDTO.getRegulations() != null) {
                Collection<GroupRegulation> regulations = reqDTO.getRegulations()
                        .stream()
                        .map(dto -> {
                            GroupRegulation model = GroupRegulation.builder()
                                    .group(reqModel)
                                    .isActive(dto.getIsActive())
                                    .isAllowed(dto.getIsAllowed())
                                    .regulation(regulationService.findById(dto.getRegulationId()))
                                    .build();
                            return model;
                        })
                        .collect(Collectors.toList());
                reqModel.setGroupRegulations(regulations);
            }

            /* Set schedules */
            if (reqDTO.getSchedules() != null) {
                Collection<GroupSchedule> schedules = reqDTO.getSchedules()
                        .stream()
                        .map(dto -> {
                            List<GroupSchedule> models = new ArrayList<>();
                            for (String s : dto.getTimeRange()) {
                                GroupSchedule model = GroupSchedule.builder()
                                        .group(reqModel)
                                        .schedule(scheduleService.findById(dto.getScheduleId()))
                                        .startTime(s.substring(0, s.indexOf("-") - 1))
                                        .endTime(s.substring(s.indexOf("-") + 2))
                                        .build();
                                models.add(model);
                            }
                            return models;
                        })
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
                reqModel.setGroupSchedules(schedules);
            }

            Group resModel = groupService.create(reqModel);

            // log created group
            logger.info("CREATED Group with id: " + resModel.getGroupId());

            GroupDTOResponse resDTO = modelMapper.map(resModel, GroupDTOResponse.class);
            resDTOs.add(resDTO);
        });

        // log success create groups
        logger.info("SUCCESSFUL - Create group(s)");
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    /**
     * Update group information
     *
     * @param reqDTO  request dto
     * @param groupId group id to update
     * @return
     */
    @PutMapping("/groups/{groupId}")
    public ResponseEntity<?> updateGroup(@Valid @RequestBody GroupDTOUpdate reqDTO,
                                         @PathVariable Integer groupId) {
        //log start update
        logger.info("START - updating group");
        Group existedModel = groupService.findById(groupId);

        modelMapper.map(reqDTO, existedModel);
        Group resModel = groupService.update(existedModel);
        GroupDTOResponse resDTO = modelMapper.map(resModel, GroupDTOResponse.class);

        //log end update
        logger.info("SUCCESSFUL - updating group");

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
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
        Group resModel = groupService.findById(groupId);
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
    public ResponseEntity<?> getGroupsByVendorId(@PathVariable Long vendorId,
                                                 @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                 @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        //log start
        logger.info("START - Get group by vendor with id: " + vendorId);
        List<GroupDTOResponse> resDTOs = groupService.getByVendorId(vendorId, size, page - 1)
                .stream().map(group -> modelMapper.map(group, GroupDTOResponse.class))
                .collect(Collectors.toList());

        logger.info("SUCCESSFUL - Get group by vendorId");
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
