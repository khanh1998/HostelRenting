package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.combination.TypeAndGroupDTO;
import org.avengers.capstone.hostelrenting.dto.combination.TypesAndGroupsDTO;
import org.avengers.capstone.hostelrenting.dto.facility.FacilityDTOCreate;
import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.dto.type.*;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@Validated
@RestController
@Transactional
@RequestMapping("/api/v1")
public class TypeController {

    private static final Logger logger = LoggerFactory.getLogger(TypeController.class);

    private TypeService typeService;
    private GroupService groupService;
    private FacilityService facilityService;
    private ModelMapper modelMapper;

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

        Collection<TypeDTOResponse> resDTOs = createType(reqDTOs, groupId);

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
                    typeService.countAvailableRoomAndCurrentBooking(hostelType);
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
                                      @RequestParam(required = false) Integer[] uCategoryIds,
                                      @RequestParam(required = false) Integer[] facilityIds,
                                      @RequestParam(required = false) Integer[] serviceIds,
                                      @RequestParam(required = false) Integer[] regulationIds,
                                      @RequestParam(required = false) Integer requestId,
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
            model.setView(model.getView() + 1);
            model = typeService.update(model);
            model = typeService.countAvailableRoomAndCurrentBooking(model);
            if (model.isDeleted()) {
                message = String.format("Type with {id=%s} was not found!", typeId);
                ApiSuccess<?> apiSuccess = new ApiSuccess<>(null, message);
                return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
            }
            TypeDTOResponse typeDTOResponse = modelMapper.map(model, TypeDTOResponse.class);
            GroupDTOResponse resGroupDTO = modelMapper.map(groupService.findById(typeDTOResponse.getGroupId()), GroupDTOResponse.class);
            TypeAndGroupDTO resDTO = TypeAndGroupDTO.builder().group(resGroupDTO).type(typeDTOResponse).build();
            // log when typeId != null
            logger.info(message);

            ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, message);

            return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
        }

        Collection<Type> types = typeService.searchWithMainFactors(latitude, longitude, distance, schoolId, provinceId, requestId, sortBy, asc, size, page);
        types = typeService.filtering(types, requestId, schoolId, provinceId, categoryId, minPrice, maxPrice, minSuperficiality, maxSuperficiality, minCapacity, maxCapacity, uCategoryIds, facilityIds, serviceIds, regulationIds, size, page-1);
        List<TypeDTOResponse> typeDTOs = types
                .stream()
                .map(type -> modelMapper.map(type, TypeDTOResponse.class))
                .collect(Collectors.toList());

        if (typeDTOs.isEmpty())
            message = "There is no hostel type";
        else
            message = "Hostel type(s) has been retrieved successfully!";

        Set<Group> groups = typeDTOs.stream()
                .map(typeDTO -> groupService.findById(typeDTO.getGroupId()))
                .collect(Collectors.toSet());
//                .collect(Collectors.groupingBy(GroupDTOResponse::getGroupId))
//                .values().stream().flatMap(List::stream).collect(Collectors.toSet());
        Set<GroupDTOResponse> groupDTOs = groups.stream()
                .map(group -> modelMapper.map(group, GroupDTOResponse.class))
                .collect(Collectors.toSet());

        int totalType = typeDTOs.size();
        int totalGroup = groupDTOs.size();

        // DTO contains list of Types and groups follow that type
        TypesAndGroupsDTO resDTO = TypesAndGroupsDTO
                .builder()
                .types(typeDTOs)
                .groups(groupDTOs)
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

    private Collection<TypeDTOResponse> createType(Collection<TypeDTOCreate> reqDTOs, Integer groupId) {
        // check existed Group
        Group exGroup = groupService.findById(groupId);

        //map dto -> model
        Collection<Type> reqModels = reqDTOs
                .stream()
                .map(reqDTO -> {
                    // create new ref obj
                    createRefObj(reqDTO);

                    Type model = modelMapper.map(reqDTO, Type.class);
                    return model;
                })
                .collect(Collectors.toList());

        return reqModels
                .stream()
                .map(reqModel -> {
                    //prepare object
                    reqModel.setGroup(exGroup);
                    setTypeForNewRoom(reqModel.getRooms(), reqModel);
                    setTypeForNewImage(reqModel.getTypeImages(), reqModel);
                    setTypeFacilities(reqModel.getTypeFacilities(), reqModel);

                    Type resModel = typeService.create(reqModel);

                    return modelMapper.map(resModel, TypeDTOResponse.class);
                })
                .collect(Collectors.toList());
    }

    private void setTypeForNewRoom(Collection<Room> rooms, Type type) {
        rooms.forEach(room -> {
            room.setType(type);
        });
    }

    private void setTypeForNewImage(Collection<TypeImage> images, Type type) {
        images.forEach(typeImage -> {
            typeImage.setType(type);
        });
    }

    private void setTypeFacilities(Collection<TypeFacility> facilities, Type type) {
        facilities.forEach(typeFacility -> {
            typeFacility.setType(type);
            typeFacility.setFacility(facilityService.findById(typeFacility.getFacility().getFacilityId()));
        });
    }

    private void createRefObj(TypeDTOCreate typeDTO) {
        if (typeDTO.getNewFacilities() != null && !typeDTO.getNewFacilities().isEmpty()) {
            typeDTO.getNewFacilities().stream().forEach(facilityDTO -> {
                Facility reqModel = modelMapper.map(facilityDTO, Facility.class);
                Facility newModel = facilityService.createNew(reqModel);
                Collection<TypeFacilityDTOCreate> facilities = typeDTO.getFacilities();
                TypeFacilityDTOCreate typeFacility = new TypeFacilityDTOCreate();
                typeFacility.setFacilityId(newModel.getFacilityId());
                facilities.add(typeFacility);
            });
        }
    }
}
