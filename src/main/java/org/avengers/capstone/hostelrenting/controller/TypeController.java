package org.avengers.capstone.hostelrenting.controller;

import com.sun.mail.iap.Response;
import org.avengers.capstone.hostelrenting.dto.combination.TypeAndGroupDTO;
import org.avengers.capstone.hostelrenting.dto.combination.TypesAndGroupsDTO;
import org.avengers.capstone.hostelrenting.dto.feedback.FeedbackDTOResponse;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOCreate;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOUpdate;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@Validated
@RestController
@RequestMapping("/api/v1")
public class TypeController {

    private static final Logger logger = LoggerFactory.getLogger(TypeController.class);

    private TypeService typeService;
    private GroupService groupService;
    private CategoryService categoryService;
    private TypeStatusService typeStatusService;
    private FacilityService facilityService;
    private FeedbackService feedbackService;
    private ModelMapper modelMapper;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

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
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setHostelTypeService(TypeService typeService) {
        this.typeService = typeService;
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
            Type reqModel = modelMapper.map(reqDTO, Type.class);
            // set hostel group
            Group group = groupService.findById(groupId);

            // set status of type
            TypeStatus typeStatus = typeStatusService.findById(reqDTO.getStatusId());
            // set facilities
            Collection<Facility> facilities = Arrays.stream(reqDTO.getFacilityIds()).map(id -> facilityService.findById(id)).collect(Collectors.toList());
            // set images
            Collection<TypeImage> images = Arrays.stream(reqDTO.getImageUrls()).map(imgUrl -> {
                TypeImage imgModel = TypeImage.builder().resourceUrl(imgUrl).build();
                imgModel.setType(reqModel);
                return imgModel;
            }).collect(Collectors.toList());
            // set rooms
            Collection<Room> rooms = Arrays.stream(reqDTO.getRoomNames()).map(room -> {
                Room roomModel = Room.builder().roomName(room).isAvailable(true).build();
                roomModel.setType(reqModel);
                return roomModel;
            }).collect(Collectors.toList());
            // set data for model
            reqModel.setRooms(rooms);
            reqModel.setTypeStatus(typeStatus);
            reqModel.setGroup(group);
            reqModel.setFacilities(facilities);
            reqModel.setTypeImages(images);
            // create model
            Type resModel = typeService.create(reqModel);
            // log created type
            if (resModel != null) {
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
        List<TypeDTOResponse> resDTOs = typeService.findByHostelGroupId(groupId).stream()
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
     * @param typeId            type id
     * @param schoolId          school id
     * @param provinceId        province id
     * @param categoryId        category id
     * @param latitude          latitude
     * @param longitude         longtitude
     * @param distance          distance from the search point
     * @param minPrice          min price for filter
     * @param maxPrice          max price for filter
     * @param minSuperficiality min superficiality for filter
     * @param maxSuperficiality mas superficiality for filter
     * @param minCapacity       min capacity for filter
     * @param maxCapacity       max capacity for filter
     * @param facilityIds       facility ids for searching
     * @param serviceIds        service ids for searching
     * @param sortBy            string for sortBy
     * @param asc               false for desc
     * @param size              size of a page
     * @param page              number of page
     * @return collection of types
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
                                      @RequestParam(required = false) Integer[] regulationIds,
                                      @RequestParam(required = false, defaultValue = "score") String sortBy,
                                      @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                      @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                      @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        //log start
        String message;

        if (typeId != null) {
            message = "Hostel type {id=" + typeId + "} has been retrieved successfully!";
            // handle hostel type and corresponding hostel group
            Type model = typeService.findById(typeId);
            model = typeService.countAvailableRoomAndCurrentBooking(model);
            TypeDTOResponse typeDTOResponse = modelMapper.map(model, TypeDTOResponse.class);
            GroupDTOResponse resGroupDTO = modelMapper.map(groupService.findById(typeDTOResponse.getGroupId()), GroupDTOResponse.class);
            TypeAndGroupDTO resDTO = TypeAndGroupDTO.builder().group(resGroupDTO).type(typeDTOResponse).build();
            // log when typeId != null
            logger.info(message);

            ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, message);

            return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
        }

        List<TypeDTOResponse> typeDTOs = typeService.searchWithMainFactors(latitude, longitude, distance, schoolId, provinceId, sortBy, asc, size, page).stream()
                .filter(hostelType -> {
                    if (categoryId != null)
                        return hostelType.getGroup().getCategory().getCategoryId() == categoryId;
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
                        return hostelType.getGroup().getGroupServices()
                                .stream()
                                .anyMatch(serviceDetail -> Arrays
                                        .stream(serviceIds)
                                        .anyMatch(id -> id.equals(serviceDetail.getGroupServiceId())));
                    return true;
                }).filter(hostelType -> {
                    if (regulationIds != null && regulationIds.length > 0)
                        return hostelType.getGroup().getGroupRegulations()
                                .stream()
                                .anyMatch(regulation -> Arrays
                                        .stream(regulationIds)
                                        .anyMatch(id -> id == regulation.getRegulation().getRegulationId()));
                    return true;
                })
                .map(hostelType -> modelMapper.map(hostelType, TypeDTOResponse.class)
                )
                .collect(Collectors.toList());

        if (typeDTOs.isEmpty())
            message = "There is no hostel type";
        else
            message = "Hostel type(s) has been retrieved successfully!";

        Set<GroupDTOResponse> groupDTOs = typeDTOs.stream()
                .map(typeDTO -> modelMapper
                        .map(groupService.findById(typeDTO.getGroupId()), GroupDTOResponse.class))
                .collect(Collectors.toSet());

        int totalType = typeDTOs.size();
        int totalGroup = groupDTOs.size();

        Set<GroupDTOResponse> resGroups = typeDTOs.stream()
                .map(typeDTO -> modelMapper.map(groupService.findById(typeDTO.getGroupId()), GroupDTOResponse.class))
                .collect(Collectors.toSet());

        // DTO contains list of Types and groups follow that type
        TypesAndGroupsDTO resDTO = TypesAndGroupsDTO
                .builder()
                .types(typeDTOs)
                .groups(resGroups)
                .totalType(totalType)
                .totalGroup(totalGroup)
                .build();

        //log success
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, message, size, page);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("types/{typeId}")
    public ResponseEntity<?> updateType(@Valid @RequestBody TypeDTOUpdate reqDTO,
                                         @PathVariable Integer typeId) {
        // log start update
        logger.info("START - updating type");
        Type existedModel = typeService.findById(typeId);
        modelMapper.map(reqDTO, existedModel);

        Type resModel = typeService.update(existedModel);
        TypeDTOResponse resDTO = modelMapper.map(resModel, TypeDTOResponse.class);

        // log end update
        logger.info("SUCCESSFUL - updating type");

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your Hostel Type has been updated successfully");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
