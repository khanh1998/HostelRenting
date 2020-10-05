package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.combination.TypeAndGroupDTO;
import org.avengers.capstone.hostelrenting.dto.combination.TypesAndGroupsDTO;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.hosteltype.TypeDTOCreate;
import org.avengers.capstone.hostelrenting.dto.hosteltype.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@Validated
@RestController
@RequestMapping("/api/v1")
public class HostelTypeController {

    private static final Logger logger = LoggerFactory.getLogger(HostelTypeController.class);

    private HostelTypeService hostelTypeService;
    private HostelGroupService hostelGroupService;
    private CategoryService categoryService;
    private TypeStatusService typeStatusService;
    private FacilityService facilityService;
    private ModelMapper modelMapper;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setTypeStatusService(TypeStatusService typeStatusService) {
        this.typeStatusService = typeStatusService;
    }

    @Autowired
    public void setFacilityService(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Create new Type with group Id
     *
     * @param groupId id of group
     * @param reqDTOs list of request hostel type dto
     * @return list of created hostel type model that converted to dto
     * @throws EntityNotFoundException when group id is not found
     */
    @PostMapping("groups/{groupId}/types")
    public ResponseEntity<?> createNewType(@PathVariable Integer groupId,
                                           @Valid @RequestBody List<TypeDTOCreate> reqDTOs) throws EntityNotFoundException {
        // Log start
        logger.info("START - Creating Type");

        List<TypeDTOResponse> resDTOs = new ArrayList<>();
        // handle each of request DTO
        reqDTOs.forEach(reqDTO -> {
            HostelType reqModel = modelMapper.map(reqDTO, HostelType.class);
            // set hostel group
            HostelGroup hostelGroup = hostelGroupService.findById(groupId);
            // set category
            Category category = categoryService.findById(reqDTO.getCategoryId());
            // set status of type
            TypeStatus typeStatus = typeStatusService.findById(reqDTO.getStatusId());
            // set facilities
            Collection<Facility> facilities = Arrays.stream(reqDTO.getFacilityIds()).map(id -> facilityService.findById(id)).collect(Collectors.toList());
            // set images
            Collection<TypeImage> images = Arrays.stream(reqDTO.getImageUrls()).map(imgUrl -> {
                TypeImage imgModel = TypeImage.builder().resourceUrl(imgUrl).build();
                imgModel.setHostelType(reqModel);
                return imgModel;
            }).collect(Collectors.toList());
            // set rooms
            Collection<HostelRoom> rooms = Arrays.stream(reqDTO.getRoomNames()).map(room -> {
                HostelRoom roomModel = HostelRoom.builder().roomName(room).isAvailable(true).build();
                roomModel.setHostelType(reqModel);
                return roomModel;
            }).collect(Collectors.toList());
            // set data for model
            reqModel.setHostelRooms(rooms);
            reqModel.setCategory(category);
            reqModel.setTypeStatus(typeStatus);
            reqModel.setHostelGroup(hostelGroup);
            reqModel.setFacilities(facilities);
            reqModel.setTypeImages(images);
            // create model
            HostelType resModel = hostelTypeService.create(reqModel);
            // log created type
            if (resModel!= null){
                logger.info("CREATED Type with id: " + reqModel.getTypeId());
            }
            // mapping to response
            TypeDTOResponse resDTO = modelMapper.map(resModel, TypeDTOResponse.class);
            resDTOs.add(resDTO);
        });

        // log success creating
        logger.info("SUCCESSFULL - Creating type");
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Hostel type has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    /**
     * Get all hostel types by group id
     *
     * @param groupId to get hostel type
     * @return list of hostel type
     * @throws EntityNotFoundException when groupId is not found
     */
    @GetMapping("/groups/{groupId}/types")
    public ResponseEntity<?> getTypeByGroupId(@PathVariable Integer groupId) throws EntityNotFoundException {
        logger.info("START - get Types by group id: " + groupId);
        String message = "Hostel types has been retrieved successfully!";
        List<TypeDTOResponse> resDTOs = hostelTypeService.findByHostelGroupId(groupId).stream()
                .map(hostelType -> {
                    logger.info("RETRIEVED Type with id: " + hostelType.getTypeId());
                    return modelMapper.map(hostelType, TypeDTOResponse.class);
                })
                .collect(Collectors.toList());

        if (resDTOs.isEmpty()) {
            message = "There is no hostel type!";
        }

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, message);
        logger.info("SUCCESSFULLY - Get types by groupId !");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    /**
     * Get types with many criteria
     *
     * @param typeId
     * @param schoolId
     * @param provinceId
     * @param categoryId
     * @param latitude
     * @param longitude
     * @param distance
     * @param minPrice
     * @param maxPrice
     * @param minSuperficiality
     * @param maxSuperficiality
     * @param minCapacity
     * @param maxCapacity
     * @param facilityIds
     * @param serviceIds
     * @param sortBy
     * @param asc
     * @param size
     * @param page
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/types")
    public ResponseEntity<?> getTypes(@RequestParam(required = false) Integer typeId,
                                            @RequestParam(required = false) Integer schoolId,
                                            @RequestParam(required = false) Integer provinceId,
                                            @RequestParam(required = false) Integer categoryId,
                                            @RequestParam(required = false) Double latitude,
                                            @RequestParam(required = false) Double longitude,
                                            @RequestParam(required = false, defaultValue = "5.0") Double distance,
                                            @RequestParam(required = false) Float minPrice,
                                            @RequestParam(required = false) Float maxPrice,
                                            @RequestParam(required = false) Float minSuperficiality,
                                            @RequestParam(required = false) Float maxSuperficiality,
                                            @RequestParam(required = false) Integer minCapacity,
                                            @RequestParam(required = false) Integer maxCapacity,
                                            @RequestParam(required = false) Integer[] facilityIds,
                                            @RequestParam(required = false) Integer[] serviceIds,
                                            @RequestParam(required = false, defaultValue = "score") String sortBy,
                                            @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                            @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                            @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) throws EntityNotFoundException {
        //log start
        logger.info("START - Get type(s)");
        if (typeId != null) {
            String message = "Hostel type {id=" + typeId + "} has been retrieved successfully!";
            // handle hostel type and corresponding hostel group
            TypeDTOResponse typeDTOResponse = modelMapper.map(hostelTypeService.findById(typeId), TypeDTOResponse.class);
            GroupDTOResponse resGroupDTO = modelMapper.map(hostelGroupService.findById(typeDTOResponse.getGroupId()), GroupDTOResponse.class);
            TypeAndGroupDTO resDTO = TypeAndGroupDTO.builder().groupDTOFull(resGroupDTO).type(typeDTOResponse).build();
            // log when typeId != null
            logger.info(message);

            ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, message);

            return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
        }

        Set<TypeDTOResponse> typeDTOs = hostelTypeService.searchWithMainFactors(latitude, longitude, distance, schoolId, provinceId, sortBy, asc, size, page).stream()
                .filter(hostelType -> {
                    if (categoryId != null)
                        return hostelType.getCategory().getCategoryId() == categoryId;
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
                }).filter(hostelType -> {
                    if (facilityIds != null && facilityIds.length > 0)
                        return hostelType.getFacilities()
                                .stream()
                                .anyMatch(facility -> Arrays
                                        .stream(facilityIds)
                                        .anyMatch(id -> id == facility.getFacilityId()));
                    return true;
                }).filter(hostelType -> {
                    if (serviceIds != null && serviceIds.length > 0)
                        return hostelType.getHostelGroup().getServiceDetails()
                                .stream()
                                .anyMatch(serviceDetail -> Arrays
                                        .stream(serviceIds)
                                        .anyMatch(id -> id == serviceDetail.getServiceId()));
                    return true;
                })
                .map(hostelType -> modelMapper.map(hostelType, TypeDTOResponse.class))
                .collect(Collectors.toSet());



        Set<GroupDTOResponse> groupDTOs = typeDTOs.stream()
                .map(typeDTO -> modelMapper.map(hostelGroupService.findById(typeDTO.getGroupId()), GroupDTOResponse.class))
                .collect(Collectors.toSet());
        groupDTOs.forEach(GroupDTOResponse::getServiceForDisplay);

        // DTO contains list of Types and groups follow that type
        TypesAndGroupsDTO resDTO = new TypesAndGroupsDTO(typeDTOs, groupDTOs);

        //log success
        logger.info("SUCCESSFULLY - Get type(s) ");
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Hostel type(s) has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
