package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.FacilityDTO;
import org.avengers.capstone.hostelrenting.dto.hostelgroup.HostelGroupDTOFull;
import org.avengers.capstone.hostelrenting.dto.combination.TypesAndGroupsDTO;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ReqTypeDTO;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Facility;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.service.FacilityService;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class HostelTypeController {

    private HostelTypeService hostelTypeService;
    private ModelMapper modelMapper;
    private HostelGroupService hostelGroupService;
    private FacilityService facilityService;

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


    @PostMapping("groups/{groupId}/types")
    public ResponseEntity<?> createNewType(@PathVariable Integer groupId,
                                    @Valid @RequestBody ReqTypeDTO reqDTO) throws EntityNotFoundException {
        HostelType reqModel = modelMapper.map(reqDTO, HostelType.class);
        HostelGroup hostelGroup = hostelGroupService.findById(groupId);
        reqModel.setHostelGroup(hostelGroup);
        reqModel.setHostelRooms(null);
        //TODO: category and typestatus
        HostelType resModel = hostelTypeService.save(reqModel);
        ResTypeDTO resDTO = modelMapper.map(resModel, ResTypeDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Hostel type has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/groups/{groupId}/types")
    public ResponseEntity<?> getHostelTypeByHostelGroupId(@PathVariable Integer groupId,
                                                          @RequestParam(required = false) Integer typeId,
                                                          @RequestParam(required = false) Float minPrice,
                                                          @RequestParam(required = false) Float maxPrice,
                                                          @RequestParam(required = false) Float minSuperficiality,
                                                          @RequestParam(required = false) Float maxSuperficiality,
                                                          @RequestParam(required = false) Integer minCapacity,
                                                          @RequestParam(required = false) Integer maxCapacity) throws EntityNotFoundException {

        String message = "Hostel types has been retrieved successfully!";
        List<ResTypeDTO> resDTOs = hostelTypeService.findByHostelGroupId(groupId).stream()
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
                })
                .map(hostelType -> modelMapper.map(hostelType, ResTypeDTO.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty()) {
            message = "There is no hostel types found!";
        }

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Hostel type has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/types")
    public ResponseEntity<?> getHostelTypes(@RequestParam(required = false) Integer typeId,
                                            @RequestParam(required = false) Integer schoolId,
                                            @RequestParam(required = false) Integer districtId,
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

        Set<ResTypeDTO> typeDTOs = hostelTypeService.searchWithMainFactors(latitude, longitude, distance, schoolId, districtId, sortBy, asc, size, page).stream()
                .filter(hostelType -> {
                    if (typeId != null)
                        return hostelType.getTypeId() == typeId;
                    return true;
                })
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
                                        .anyMatch(id -> true ? Integer.compare(id, facility.getFacilityId()) == 0 : false));
                    return true;
                }).filter(hostelType -> {
                    if (serviceIds != null && serviceIds.length > 0)
                        return hostelType.getHostelGroup().getServiceDetails()
                                .stream()
                                .anyMatch(serviceDetail -> Arrays
                                        .stream(serviceIds)
                                        .anyMatch(id -> true ? Integer.compare(id, serviceDetail.getServiceId()) == 0 : false));
                    return true;
                })
                .map(hostelType -> modelMapper.map(hostelType, ResTypeDTO.class))
                .collect(Collectors.toSet());

        Set<HostelGroupDTOFull> groupDTOs = typeDTOs.stream()
                .map(typeDTO -> modelMapper.map(hostelGroupService.findById(typeDTO.getGroupId()), HostelGroupDTOFull.class))
                .collect(Collectors.toSet());
        groupDTOs.forEach(hostelGroupDTO -> hostelGroupDTO.getServiceForDisplay());


        // DTO contains list of Types and groups follow that type
        TypesAndGroupsDTO resDTO = new TypesAndGroupsDTO(typeDTOs, groupDTOs);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Hostel type has been retrieved successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


    /**
     * Add Facilities to hostel type
     *
     * @param typeId hostel type id
     * @param facilities list of facilities
     * @return ResponseEntity
     */
    @PostMapping("types/{typeId}/facilities")
    public ResponseEntity<?> addFacility(@PathVariable Integer typeId,
                                         @Valid @RequestBody List<FacilityDTO> facilities) {
        HostelType typeModel = hostelTypeService.findById(typeId);
        typeModel.setHostelRooms(null);
        Set<Facility> matchedFacilities = facilities
                .stream()
                .filter(f -> {
                    Facility existedFacility = facilityService.findById(f.getFacilityId());
                    f.setFacilityName(existedFacility.getFacilityName());
                    return existedFacility != null;
                }).map(f -> modelMapper.map(f, Facility.class))
                .collect(Collectors.toSet());


        typeModel.setFacilities(matchedFacilities);
        hostelTypeService.save(typeModel);
        ResTypeDTO resDTO = modelMapper.map(typeModel, ResTypeDTO.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Facilities has been added successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

}
