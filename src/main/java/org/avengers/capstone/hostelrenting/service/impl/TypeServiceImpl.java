package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.room.RoomDTOCreate;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.type.TypeFacilityDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.TypeFacilityRepository;
import org.avengers.capstone.hostelrenting.repository.TypeRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.util.Utilities;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TypeServiceImpl implements TypeService {
    private TypeRepository typeRepository;
    private TypeFacilityRepository typeFacilityRepository;
    private GroupService groupService;
    private ProvinceService provinceService;
    private SchoolService schoolService;
    private HostelRequestService hostelRequestService;
    private UtilityService utilityService;
    private Utilities utilities;
    private ModelMapper mapper;
    private TypeImageService typeImageService;
    private TypeFacilityService typeFacilityService;
    private RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }

    @Autowired
    public void setUtilityService(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @Autowired
    public void setHostelRequestService(HostelRequestService hostelRequestService) {
        this.hostelRequestService = hostelRequestService;
    }

    @Autowired
    public void setTypeFacilityRepository(TypeFacilityRepository typeFacilityRepository) {
        this.typeFacilityRepository = typeFacilityRepository;
    }


    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Autowired
    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    private static final Logger logger = LoggerFactory.getLogger(TypeServiceImpl.class);

    @Autowired
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setHostelTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setTypeImageService(TypeImageService typeImageService) {
        this.typeImageService = typeImageService;
    }

    @Autowired
    public void setTypeFacilityService(TypeFacilityService typeFacilityService) {
        this.typeFacilityService = typeFacilityService;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<Type> model = typeRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Type.class, "id", id.toString());
    }

    @Override
    public Type findById(Integer id) {
        checkExist(id);
        Type resModel = typeRepository.getOne(id);
//        handleTypeForEndUser(resModel);

        return resModel;
    }

    @Override
    public Type create(Type reqModel) {
        //TODO: check duplicate
        // Set createdAt
        reqModel.setCreatedAt(System.currentTimeMillis());

        return typeRepository.save(reqModel);
    }

    @Override
    public Type update(Type reqModel) {
        // Set updatedAt
        reqModel.setUpdatedAt(System.currentTimeMillis());

        return typeRepository.save(reqModel);
    }

    private void updateImages(TypeDTOUpdate dto, Type type) {
        Collection<TypeImage> newImages = dto.getImageUrls();
        Collection<TypeImage> oldImages = type.getTypeImages();
        if (newImages != null) {
            List<Integer> deletedImages = oldImages.stream()
                    .filter(oldImage -> !newImages.contains(oldImage))
                    .map(TypeImage::getImageId)
                    .collect(Collectors.toList());
            typeImageService.deleteByIds(deletedImages);
            List<TypeImage> addedImages = newImages.stream()
                    .filter(newImage -> !oldImages.contains(newImage))
                    .map(image -> {
                        image.setDeleted(false);
                        image.setType(type);
                        return typeImageService.save(image);
                    })
                    .collect(Collectors.toList());
            dto.setImageUrls(addedImages);
        }
    }
    @Override
    public Type updatePartial(TypeDTOUpdate dto, int typeId) {
        Type type = findById(typeId);
        updateImages(dto, type);
        updateFacilities(dto, type);
        updateRooms(dto, type);
        mapper.map(dto, type);
        return typeRepository.save(type);
    }

    private void updateRooms(TypeDTOUpdate dto, Type type) {
        Collection<Room> oldRooms = type.getRooms();
        Collection<Room> newRooms = dto.getRooms();
        List<Integer> oldRoomIds = oldRooms.stream().map(Room::getRoomId).collect(Collectors.toList());
        List<Integer> newRoomIds = newRooms.stream().map(Room::getRoomId).collect(Collectors.toList());
        System.out.println(oldRoomIds);
        System.out.println(newRoomIds);
        if (!newRooms.isEmpty()) {
            List<Integer> deletedRoomIds = oldRooms.stream()
                    .filter(room -> !newRoomIds.contains(room.getRoomId()))
                    .map(room -> {
                        roomService.deleteById(room.getRoomId());
                        return room.getRoomId();
                    })
                    .collect(Collectors.toList());
            List<Room> addedRooms = newRooms.stream()
                    .map(room -> {
                        room.setType(type);
                        if (!oldRoomIds.contains(room.getRoomId())) {
                            System.out.println(room);
                            return roomService.save(room);
                        }
                        return room;
                    })
                    .collect(Collectors.toList());
            Predicate<Room> delete = room -> deletedRoomIds.contains(room.getRoomId());
            oldRooms.removeIf(delete);
            dto.setRooms(addedRooms);
        }
    }

    private void updateFacilities(TypeDTOUpdate dto, Type type) {
        Collection<TypeFacility> newFacilities = dto.getFacilities();
        Collection<TypeFacility> oldFacilities = type.getTypeFacilities();
        List<Integer> newFacilityIds = newFacilities.stream()
                .map(TypeFacility::getId)
                .collect(Collectors.toList());
        List<Integer> oldFacilityIds = oldFacilities.stream()
                .map(TypeFacility::getId)
                .collect(Collectors.toList());
        if (!newFacilities.isEmpty()) {
            List<Integer> deletedTypeFacilities = oldFacilities.stream()
                    .filter(typeFacility -> !newFacilityIds.contains(typeFacility.getId()))
                    .map(TypeFacility::getId)
                    .collect(Collectors.toList());
            typeFacilityService.deleteById(deletedTypeFacilities);
            List<TypeFacility> addedTypeFacility = newFacilities.stream()
                    .map(typeFacility -> {
                        typeFacility.setType(type);
                        if (!oldFacilityIds.contains(typeFacility.getId())) {
                            return typeFacilityService.create(typeFacility);
                        }
                        return typeFacility;
                    })
                    .collect(Collectors.toList());
            Predicate<TypeFacility> delete = typeFacility -> deletedTypeFacilities.contains(typeFacility.getId());
            oldFacilities.removeIf(delete);
            dto.setFacilities(addedTypeFacility);
        }
    }

    @Override
    public void deleteById(Integer id) {
        //TODO: Implement
    }

    /**
     * Get all types by groupId
     *
     * @param hostelGroupId
     * @return HostelTypes of given group, otherwise null
     */
    @Override
    public List<Type> findByHostelGroupId(Integer hostelGroupId) {
        groupService.checkExist(hostelGroupId);
        List<Type> types = typeRepository.findByGroup_GroupId((hostelGroupId));
        return types;
    }

    /**
     * Default: hosteltype with score
     * Retain location with concat schoolmate and compatriot
     *
     * @param latitude
     * @param longitude
     * @param distance
     * @param sortBy
     * @param asc
     * @param size
     * @param page
     * @return
     */
    @Override
    public Collection<Type> searchWithMainFactors(Double latitude, Double longitude, Double distance, Integer schoolId, Integer provinceId, Integer requestId, String sortBy, Boolean asc, int size, int page) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Collection<Type> locTypes;
        Collection<Type> schoolMateTypesOnly = new ArrayList<>();
        Collection<Type> compatriotTypesOnly = new ArrayList<>();
        boolean basicSearch = true;

        /* Get surrounding based on long and lat */
        if ((latitude != null && longitude != null) || requestId != null) {
            locTypes = getSurrouding(longitude, latitude, distance, requestId);
        } else {
            //TODO: implement get default
            locTypes = typeRepository.findTopOrderByScore(size, (page - 1) * size);
        }
        locTypes = handleAfterRetrieve(locTypes);

        /* list of return types */
        Collection<Type> result = new ArrayList<>(locTypes);

        /* list of type has only schoolMate */
        if (schoolId != null) {
            //check whether schoolId exist or not
            schoolService.findById(schoolId);

            basicSearch = false;
            schoolMateTypesOnly = convertMapToList(typeRepository.getBySchoolMates(schoolId), 1);
            if (schoolMateTypesOnly.size() > 0)
                schoolMateTypesOnly.retainAll(result);
        }

        /* list of type has only compatriot */
        if (provinceId != null) {
            //check whether provinceId exist or not
            provinceService.findById(provinceId);

            basicSearch = false;
            compatriotTypesOnly = convertMapToList(typeRepository.getByCompatriot(provinceId), 2);
            if (compatriotTypesOnly.size() > 0)
                compatriotTypesOnly.retainAll(result);
        }

        /* Generate result collection and sort */
        result = generateResult(result, schoolMateTypesOnly, compatriotTypesOnly);

        /* Immediately return if only search by location */
        if (basicSearch)
            return result;

        if (!basicSearch) {
            result = result.stream()
                    .filter(type -> type.getCompatriot() + type.getSchoolmate() > 0)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        return result;
    }

    @Override
    public Collection<Type> filtering(Collection<Type> types, Integer requestId, Integer schoolId, Integer provinceId, Integer categoryId, Float minPrice, Float maxPrice, Float minSuperficiality, Float maxSuperficiality, Integer minCapacity, Integer maxCapacity, Integer[] uCategoryIds, Integer[] facilityIds, Integer[] serviceIds, Integer[] regulationIds, Integer size, Integer page) {
        HostelRequest exRequest = null;
        if (requestId != null) {
            exRequest = hostelRequestService.findById(requestId);
        }
        HostelRequest finalExRequest = exRequest;
        return types.stream().filter(type -> {
            if (categoryId != null)
                return type.getGroup().getCategory().getCategoryId() == categoryId;
            return true;
        }).filter(hostelType -> {
            if (uCategoryIds != null && uCategoryIds.length > 0) {
                return getUtilityCategory(hostelType, uCategoryIds);
            }
            return true;
        }).filter(hostelType -> {
            if (minPrice != null)
                return hostelType.getPrice() >= minPrice;
            return true;
        }).filter(hostelType -> {
            if (maxPrice != null)
                return hostelType.getPrice() <= maxPrice;
            else if (finalExRequest != null && finalExRequest.getMaxPrice() != null)
                return hostelType.getPrice() <= finalExRequest.getMaxPrice();
            return true;
        }).filter(hostelType -> {
            if (minSuperficiality != null)
                return hostelType.getSuperficiality() >= minSuperficiality;
            else if (finalExRequest != null && finalExRequest.getMinSuperficiality() != null) {
                return hostelType.getSuperficiality() >= finalExRequest.getMinSuperficiality();
            }
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
                return Arrays
                        .stream(facilityIds)
                        .allMatch(id ->
                                hostelType.getTypeFacilities()
                                        .stream()
                                        .anyMatch(typeFacility -> typeFacility.getFacility().getFacilityId() == id));
//                return hostelType.getTypeFacilities()
//                        .stream()
//                        .allMatch(typeFacility -> Arrays
//                                .stream(facilityIds)
//                                .anyMatch(id -> id == typeFacility.getFacility().getFacilityId()));
            return true;
        }).filter(hostelType -> {
            if (serviceIds != null && serviceIds.length > 0)
                return Arrays
                        .stream(serviceIds)
                        .allMatch(id ->
                                hostelType.getGroup().getGroupServices()
                                        .stream()
                                        .anyMatch(serviceDetail -> serviceDetail.getService().getServiceId() == id));
//                return hostelType.getGroup().getGroupServices()
//                        .stream()
//                        .anyMatch(serviceDetail -> Arrays
//                                .stream(serviceIds)
//                                .anyMatch(id -> id.equals(serviceDetail.getGroupServiceId())));
            return true;
        }).filter(hostelType -> {
            if (regulationIds != null && regulationIds.length > 0)
                return Arrays
                        .stream(regulationIds)
                        .allMatch(id ->
                                hostelType.getGroup().getGroupRegulations()
                                        .stream()
                                        .anyMatch(groupRegulation -> groupRegulation.getRegulation().getRegulationId() == id));
//                return hostelType.getGroup().getGroupRegulations()
//                        .stream()
//                        .anyMatch(regulation -> Arrays
//                                .stream(regulationIds)
//                                .anyMatch(id -> id == regulation.getRegulation().getRegulationId()));
            return true;
        })
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    private Collection<Type> generateResult(Collection<Type> result, Collection<Type> schoolMateTypesOnly, Collection<Type> compatriotTypesOnly) {
        return Stream.concat(result.stream(), Stream.concat(schoolMateTypesOnly.stream(), compatriotTypesOnly.stream()))
                // sort - the order is reversed
                .sorted((o1, o2) -> {
                    int s1 = o1.getSchoolmate();
                    int s2 = o2.getSchoolmate();
                    int c1 = o1.getCompatriot();
                    int c2 = o2.getCompatriot();
                    int total1 = o1.getSchoolmate() + o1.getCompatriot();
                    int total2 = o2.getSchoolmate() + o2.getCompatriot();
                    // compare type has both criteria with type has only 1
                    if ((s1 > 0 && c1 > 0) && (s2 == 0 || c2 == 0))
                        return -1;
                    // compare 2 types that have both criteria >0
                    if ((s1 > 0 && c1 > 0) && (s2 > 0 && c2 > 0))
                        return Integer.compare(total2, total1);
                    // compare 2 types that have only 1 criteria
                    if ((s1 == 0 || c1 == 0) && (s2 == 0 || c2 == 0))
                        return Integer.compare(total2, total1);
                    return 1;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /* Handling necessary business after retrieving data
     * 1. Retrieving available rooms
     * 2. Retrieving current bookings
     * 3. Filter the type has available room > 0
     */
    @Override
    public Collection<Type> handleAfterRetrieve(Collection<Type> types) {
        return types.stream().map(this::countAvailableRoomAndCurrentBooking)
                .filter(type -> type.getAvailableRoom() > 0)
                .filter(type -> !type.isDeleted())
                .filter(Type::isActive)
                .sorted(Comparator.comparing(Type::getScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * counting available room and current booking of 1 type
     *
     * @param type
     * @return
     */
    public Type countAvailableRoomAndCurrentBooking(Type type) {
        long availableRoom = type.getRooms().stream().filter(Room::isAvailable).count();
        type.setAvailableRoom((int) availableRoom);
        long currentBooking = type.getBookings().stream().filter(booking -> booking.getStatus() == Booking.STATUS.INCOMING).count();
        type.setCurrentBooking((int) currentBooking);
        return type;
    }

    /**
     * @param inputList object list that returned from procedure
     * @param code      == 1 is schoolmate, otherwise is compatriot
     * @return list of types has been converted from map to list
     */
    private List<Type> convertMapToList(List<Object[]> inputList, int code) {
        if (inputList.isEmpty())
            return new ArrayList<>();
        Map<Integer, Integer> inputMap = new HashMap<>();
        for (Object[] obj : inputList) {
            Integer typeId = (Integer) obj[0];
            Integer count = ((BigInteger) obj[1]).intValue();
            inputMap.put(typeId, count);
        }

        List<Type> types = inputMap.keySet().stream()
                .map(integer -> {
                    Type temp = findById(integer);
                    if (code == 1)
                        temp.setSchoolmate(Math.toIntExact(inputMap.get(integer)));
                    else
                        temp.setCompatriot(Math.toIntExact(inputMap.get(integer)));
                    return temp;
                }).collect(Collectors.toList());

        return types;
    }

//    private void handleTypeForEndUser(Type model){
//        model.setTypeFacilities(typeFacilityRepository.findByType_TypeIdAndFacility_IsApproved(model.getTypeId(), true));
//    }

    private Collection<Type> getSurrouding(Double longitude, Double latitude, Double distance, Integer requestId) {
        boolean byRequest = requestId != null;
        boolean byLocation = longitude != null && latitude != null;
        double mainLong;
        double mainLat;
        Double mainDistance;
        Collection<Type> types = null;
        if (byRequest || byLocation) {
            if (byRequest) {
                HostelRequest exRequest = hostelRequestService.findById(requestId);
                types = typeRepository.getSurroundings(exRequest.getLatitude(), exRequest.getLongitude(), exRequest.getMaxDistance());
                types = getTypeByRequestDueTime(types, exRequest, distance);
            } else {
                types = typeRepository.getSurroundings(latitude, longitude, distance);
            }
        }
        return types;
    }

    private Collection<Type> getTypeByRequestDueTime(Collection<Type> types, HostelRequest exRequest, Double distance) {
        // type with status ACTIVATED
        Collection<Type> typesByDueTime_activated = typeRepository.findByRequestDueTime(exRequest.getRequestId(), Contract.STATUS.ACTIVATED.toString());
        typesByDueTime_activated = typesByDueTime_activated.stream().filter(type -> {
            return Utilities.calculateDistance(type.getGroup().getLatitude(), type.getGroup().getLongitude(), exRequest.getLatitude(), exRequest.getLongitude()) <= distance;
        }).collect(Collectors.toList());

        // type with status REVERSED
        Collection<Type> typesByDueTime_reversed = typeRepository.findByRequestDueTime(exRequest.getRequestId(), Contract.STATUS.RESERVED.toString());
        typesByDueTime_reversed = typesByDueTime_reversed.stream().filter(type -> {
            return Utilities.calculateDistance(type.getGroup().getLatitude(), type.getGroup().getLongitude(), exRequest.getLatitude(), exRequest.getLongitude()) <= distance;
        }).collect(Collectors.toList());

        // mix with the old types
        types = Stream.concat(types.stream(), Stream.concat(typesByDueTime_activated.stream(), typesByDueTime_reversed.stream()))
                .collect(Collectors.toSet());
        return types;
    }

    private boolean getUtilityCategory(Type type, Integer[] uCategoryIds) {
        Group group = type.getGroup();
        Double lat1 = group.getLatitude();
        Double lng1 = group.getLongitude();
        AtomicBoolean flag = new AtomicBoolean(false);

        Arrays.stream(uCategoryIds).forEach(uCategoryId -> {
            Collection<Utility> utilities = utilityService.getUtilitiesByCategoryId(uCategoryId);
            utilities.forEach(utility -> {
                if (Utilities.calculateDistance(lat1, lng1, utility.getLatitude(), utility.getLongitude()) <= 1) {
                    Collection<Integer> uCategories = type.getuCategoryIds();
                    uCategories.add(uCategoryId);
                    type.setUCategoryIds(uCategories);
                    flag.set(true);
                }
            });
        });

        return flag.get();
    }
}
