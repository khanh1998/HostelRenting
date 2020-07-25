package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.*;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.FacilityService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.*;

@RestController
@RequestMapping("api/v1")
public class HostelTypeController {

    private HostelTypeService hostelTypeService;
    private ModelMapper modelMapper;
    private HostelGroupService hostelGroupService;
    private FacilityService facilityService;
    private ServiceService serviceService;

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) { this.hostelGroupService = hostelGroupService; }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) { this.hostelTypeService = hostelTypeService; }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/groups/{groupId}/types")
    public ResponseEntity<ApiSuccess> getHostelTypeByHostelGroupId(@PathVariable Integer groupId,
                                                                   @RequestParam(required = false) Integer typeId,
                                                                   @RequestParam(required = false) Float minPrice,
                                                                   @RequestParam(required = false) Float maxPrice,
                                                                   @RequestParam(required = false) Float minSuperficiality,
                                                                   @RequestParam(required = false) Float maxSuperficiality,
                                                                   @RequestParam(required = false) Integer minCapacity,
                                                                   @RequestParam(required = false) Integer maxCapacity,
                                                                   @RequestParam(required = false, defaultValue = "50") Integer size,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer page) throws EntityNotFoundException {
        List<HostelTypeDTO> responseHostelTypes = hostelTypeService.findByHostelGroupId(groupId).stream()
                .filter(hostelType -> {
                    if (typeId != null)
                        return hostelType.getTypeId() == typeId;
                    return true;
                }).filter(hostelType -> {
                    if (minPrice != null)
                        return hostelType.getPrice() >= minPrice;
                    return true;
                }).filter(hostelType -> {
                    if (maxPrice != null)
                        return hostelType.getPrice() <= maxPrice;
                    return true;
                }).filter(hostelType -> {
                    if (minSuperficiality != null)
                        return hostelType.getSuperficiality() >= minSuperficiality;
                    return true;
                }).filter(hostelType -> {
                    if (maxSuperficiality != null)
                        return hostelType.getSuperficiality() <= maxSuperficiality;
                    return true;
                }).filter(hostelType -> {
                    if (minCapacity != null)
                        return hostelType.getCapacity() >= minCapacity;
                    return true;
                }).filter(hostelType -> {
                    if (maxCapacity != null)
                        return hostelType.getCapacity() <= maxCapacity;
                    return true;
                }).skip(page * size)
                .limit(size)
                .map(hostelType -> modelMapper.map(hostelType, HostelTypeDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.
                status(HttpStatus.OK).
                body((new ApiSuccess(responseHostelTypes, String.format(GET_SUCCESS, HostelType.class.getSimpleName()))));
    }

    @GetMapping("/types")
    public ResponseEntity<ApiSuccess> getHostelTypes(@RequestParam(required = false) Integer typeId,
                                                     @RequestParam(required = false) String address,
                                                     @RequestParam(required = false) Float minPrice,
                                                     @RequestParam(required = false) Float maxPrice,
                                                     @RequestParam(required = false) Float minSuperficiality,
                                                     @RequestParam(required = false) Float maxSuperficiality,
                                                     @RequestParam(required = false) Integer minCapacity,
                                                     @RequestParam(required = false) Integer maxCapacity,
                                                     @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                     @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) throws EntityNotFoundException {

        Set<HostelTypeDTO> typeDTOs = hostelTypeService.findByAddress(address).stream()
                .filter(hostelType -> {
                    if (typeId != null)
                        return hostelType.getTypeId() == typeId;
                    return true;
                })
                .filter(hostelType -> {
                    if (minPrice != null)
                        return hostelType.getPrice() >= minPrice;
                    return true;
                }).filter(hostelType -> {
                    if (maxPrice != null)
                        return hostelType.getPrice() <= maxPrice;
                    return true;
                }).filter(hostelType -> {
                    if (minSuperficiality != null)
                        return hostelType.getSuperficiality() >= minSuperficiality;
                    return true;
                }).filter(hostelType -> {
                    if (maxSuperficiality != null)
                        return hostelType.getSuperficiality() <= maxSuperficiality;
                    return true;
                }).filter(hostelType -> {
                    if (minCapacity != null)
                        return hostelType.getCapacity() >= minCapacity;
                    return true;
                }).filter(hostelType -> {
                    if (maxCapacity != null)
                        return hostelType.getCapacity() <= maxCapacity;
                    return true;
                })
                .skip((page-1) * size)
                .limit(size)
                .map(hostelType -> modelMapper.map(hostelType, HostelTypeDTO.class))
                .collect(Collectors.toSet());

        Set<HostelGroupDTO> groupDTOs = typeDTOs.stream()
                .map(typeDTO -> modelMapper.map(hostelGroupService.findById(typeDTO.getGroupId()), HostelGroupDTO.class))
                .collect(Collectors.toSet());



        // DTO contains list of Types and groups follow that type
        TypesAndGroupsDTO resDTO = new TypesAndGroupsDTO(typeDTOs, groupDTOs);

        return ResponseEntity.
                status(HttpStatus.OK).
                body((new ApiSuccess(resDTO, String.format(GET_SUCCESS, HostelType.class.getSimpleName()))));
    }

    @PostMapping("groups/{groupId}/types")
    public ResponseEntity<ApiSuccess> create(@PathVariable Integer groupId,
                                             @Valid @RequestBody HostelTypeDTO rqHostelType) throws EntityNotFoundException {
        HostelType model = modelMapper.map(rqHostelType, HostelType.class);
        HostelGroup hostelGroup = hostelGroupService.findById(groupId);
        model.setHostelGroup(hostelGroup);
        model.setHostelRooms(null);
        //TODO: category and typestatus
        HostelType createdModel = hostelTypeService.save(model);
        HostelTypeDTO createdDTO = modelMapper.map(createdModel, HostelTypeDTO.class);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, HostelType.class.getSimpleName())));
    }

    @PostMapping("types/{typeId}/facilities")
    public ResponseEntity<ApiSuccess> addFacility(@PathVariable Integer typeId,
                                                  @Valid @RequestBody List<FacilityDTO> facilities) {
        HostelType typeModel = hostelTypeService.findById(typeId);
        typeModel.setHostelRooms(null);
        Set<Facility> matchedFacilities = facilities
                .stream()
                .filter(f -> {
                    Facility existedFacility = facilityService.findById(f.getFacilityId());
                    f.setFacilityName(existedFacility.getFacilityName());
                    if (existedFacility != null) {
                        return true;
                    }
                    return false;
                }).map(f -> modelMapper.map(f, Facility.class))
                .collect(Collectors.toSet());


        typeModel.setFacilities(matchedFacilities);
        hostelTypeService.save(typeModel);
        HostelTypeDTO resDTO = modelMapper.map(typeModel, HostelTypeDTO.class);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, HostelType.class.getSimpleName())));
    }

    @PostMapping("/types/{typeId}/services")
    public ResponseEntity<ApiSuccess> addService(@PathVariable Integer typeId,
                                                 @Valid @RequestBody List<ServiceDTO> services){
        HostelType typeModel = hostelTypeService.findById(typeId);
        typeModel.setHostelRooms(null);
        Set<Service> matchedServices = services
                .stream()
                .filter(s ->{
                    Service existedService = serviceService.findById(s.getServiceId());
                    s.setServiceName(existedService.getServiceName());
                    s.setServicePrice(existedService.getServicePrice());
                    s.setPriceUnit(existedService.getPriceUnit());
                    s.setUserUnit(existedService.getUserUnit());
                    if (existedService != null)
                        return true;
                    return false;
                })
                .map(s -> modelMapper.map(s, Service.class))
                .collect(Collectors.toSet());

        typeModel.setServices(matchedServices);
        hostelTypeService.save(typeModel);
        HostelTypeDTO resDTO = modelMapper.map(typeModel, HostelTypeDTO.class);

        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, HostelTypeDTO.class.getSimpleName())));
    }

//    @PutMapping("/groups/{groupId}/types/{typeId}")
//    public ResponseEntity<ApiSuccess> update(@PathVariable Integer typeId,
//                                             @PathVariable Integer groupId,
//                                             @Valid @RequestBody HostelTypeDTO rqHostelType) throws EntityNotFoundException {
//        // not able to update info
//        HostelGroup hostelGroup = hostelGroupService.findById(groupId);
//        Category category = hostelTypeService.findById(typeId).getCategory();
//
//        rqHostelType.setTypeId(typeId);
//        HostelType rqModel = modelMapper.map(rqHostelType, HostelType.class);
//        rqModel.setHostelGroup(hostelGroup);
//        rqModel.setCategory(category);
//        HostelType updatedModel = hostelTypeService.save(rqModel);
//        HostelTypeDTO updatedDTO = modelMapper.map(updatedModel, HostelTypeDTO.class);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, HostelType.class.getSimpleName())));
//    }



}
