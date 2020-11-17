package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.repository.TypeFacilityRepository;
import org.avengers.capstone.hostelrenting.repository.TypeRepository;
import org.avengers.capstone.hostelrenting.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
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
        if (latitude != null && longitude != null) {
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
    public Collection<Type> filtering(Collection<Type> types, Integer schoolId, Integer provinceId, Integer categoryId, Float minPrice, Float maxPrice, Float minSuperficiality, Float maxSuperficiality, Integer minCapacity, Integer maxCapacity, Integer[] facilityIds, Integer[] serviceIds, Integer[] regulationIds) {
        return types.stream().filter(type -> {
            if (categoryId != null)
                return type.getGroup().getCategory().getCategoryId() == categoryId;
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
        }).filter(hostelType -> {
            if (facilityIds != null && facilityIds.length > 0)
                return hostelType.getTypeFacilities()
                        .stream()
                        .anyMatch(typeFacility -> Arrays
                                .stream(facilityIds)
                                .anyMatch(id -> id == typeFacility.getFacility().getFacilityId()));
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
        }).collect(Collectors.toList());
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
                .filter(type -> type.getAvailableRoom() > 0 && !type.isDeleted())
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
                mainLong = exRequest.getLongitude();
                mainLat = exRequest.getLatitude();
                mainDistance = exRequest.getMaxDistance();
            } else {
                mainLong = longitude;
                mainLat = latitude;
                mainDistance = distance;
            }
            types = typeRepository.getSurroundings(mainLat, mainLong, mainDistance);
        }
        return types;
    }
}
