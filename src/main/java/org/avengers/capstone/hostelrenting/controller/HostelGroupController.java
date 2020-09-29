package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOShort;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class HostelGroupController {
    private HostelGroupService hostelGroupService;

    private StreetWardService streetWardService;

    private VendorService vendorService;

    private ServiceDetailService serviceDetailService;

    private ServiceService serviceService;

    private ModelMapper modelMapper;

    @Autowired
    public void setServiceDetailService(ServiceDetailService serviceDetailService) {
        this.serviceDetailService = serviceDetailService;
    }

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
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
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    @PostMapping("/groups")
//    public ResponseEntity<?> createHostelGroup(@Valid @RequestBody HostelGroupDTOShort reqDTO) throws EntityNotFoundException {
//        // get necessary for model: vendor, address, services
//        HostelGroup hostelGroupModel = modelMapper.map(reqDTO, HostelGroup.class);
//        hostelGroupModel.setVendor(vendorService.findById(reqDTO.getVendorId()));
//        hostelGroupModel.setAddress(streetWardService.findByStreetIdAndWardId(reqDTO.getAddressFull().getStreetId(), reqDTO.getAddressFull().getWardId()));
//        Collection<ServiceDetail> serviceDetails = reqDTO.getServices()
//                .stream()
//                .map(dto -> {
//                    dto.setCreatedAt(System.currentTimeMillis());
//                    ServiceDetail serviceDetail = modelMapper.map(dto, ServiceDetail.class);
//                    serviceDetail.setHGroup(hostelGroupModel);
//                    return serviceDetail;
//                })
//                .collect(Collectors.toList());
//        hostelGroupModel.setServiceDetails(serviceDetails);
//        hostelGroupService.create(hostelGroupModel);
//        HostelGroupDTOShort resDTO = modelMapper.map(hostelGroupModel, HostelGroupDTOShort.class);
//        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been created successfully!");
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
//    }

    @PostMapping("/groups")
    public ResponseEntity<?> createHostelGroup(@Valid @RequestBody List<HostelGroupDTOShort> reqDTOs) throws EntityNotFoundException {
        // get necessary for model: vendor, address, services
        List<HostelGroupDTOShort> resDTOs = new ArrayList<>();
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
            hostelGroupService.create(hostelGroupModel);
            HostelGroupDTOShort resDTO = modelMapper.map(hostelGroupModel, HostelGroupDTOShort.class);
            resDTOs.add(resDTO);
        });

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/groups/{groupId}")
    public ResponseEntity<?> updateHostelGroup(@PathVariable Integer groupId,
                                               @Valid @RequestBody HostelGroupDTOFull rqHostelGroup) throws EntityNotFoundException {
        rqHostelGroup.setGroupId(groupId);
        HostelGroup existedModel = hostelGroupService.findById(groupId);
        HostelGroup rqModel = modelMapper.map(rqHostelGroup, HostelGroup.class);
        Vendor vendor = existedModel.getVendor();

//        rqModel.setStreet(existedModel.getStreet());
        rqModel.setVendor(vendor);
        HostelGroup resModel = hostelGroupService.update(rqModel);
        HostelGroupDTOFull resDTO = modelMapper.map(resModel, HostelGroupDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been updated successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<?> getHostelGroupById(@PathVariable Integer groupId) {
        HostelGroup resModel = hostelGroupService.findById(groupId);
        HostelGroupDTOFull resDTO = modelMapper.map(resModel, HostelGroupDTOFull.class);

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/groups")
    public ResponseEntity<?> getGroupsByVendorId(@PathVariable Long vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        List<HostelGroupDTOFull> resDTOs = existedModel.getHostelGroups()
                .stream()
                .map(hostelGroup -> modelMapper.map(hostelGroup, HostelGroupDTOFull.class))
                .collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your hostel group has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
