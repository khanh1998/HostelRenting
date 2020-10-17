package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Booking;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.repository.TypeRepository;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.TypeService;
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
    private GroupService groupService;
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

        return typeRepository.getOne(id);
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
    public Collection<Type> searchWithMainFactors(Double latitude, Double longitude, Double distance, Integer schoolId, Integer provinceId, String sortBy, Boolean asc, int size, int page) {
        Sort sort = Sort.by(asc == true ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Collection<Type> locTypes;
        Collection<Type> schoolMateTypes = new ArrayList<>();
        Collection<Type> compatriotTypes = new ArrayList<>();
        if (latitude != null && longitude != null) {
            // if long&lat != null ==> get surroundings
            logger.info("START - Get surrounding based on lng, lat and distance");
            locTypes = typeRepository.getSurroundings(latitude, longitude, distance);
            logger.info("END - Get surrounding");
        } else {
            //default get ...
            //TODO: implement get default
            logger.info("START - Get default ");
            locTypes = typeRepository.findAll();
            logger.info("END - Get default");
        }

        if (schoolId != null) {
            logger.info("START - Get schoolmate");
            schoolMateTypes = convertMapToList(typeRepository.getBySchoolMates(schoolId), 1);
            logger.info("END - Get types with schoolmate");
        }

        if (provinceId != null) {
            logger.info("START - Get compatriot");
            compatriotTypes = convertMapToList(typeRepository.getByCompatriot(provinceId), 2);
            logger.info("END - Get compatriot");
        }

        // new collection to retainAll (unmodifiable collection cannot be removed)
        List<Type> temp = new ArrayList<>(locTypes);
        if (!compatriotTypes.isEmpty() || !schoolMateTypes.isEmpty()) {
            temp.retainAll(Stream.concat(compatriotTypes.stream(), schoolMateTypes.stream()).collect(Collectors.toList()));
        }
        logger.info("START - Get availableRoom and currentBooking");
        temp = temp.stream().map(this::countAvailableRoomAndCurrentBooking).filter(type -> type.getAvailableRoom()>0).collect(Collectors.toList());
        logger.info("END - Get availableRoom and currentBooking");
        return temp;
    }

    @Override
    public Type countAvailableRoomAndCurrentBooking(Type type){
        long availableRoom = type.getRooms().stream().filter(Room::isAvailable).count();
        type.setAvailableRoom((int) availableRoom);
        long currentBooking = type.getBookings().stream().filter(booking -> booking.getStatus()== Booking.STATUS.INCOMING).count();
        type.setCurrentBooking((int) currentBooking);
        return type;
    }

    /**
     * @param inputList
     * @param code == 1 is schoolmate, otherwise is compatriot
     * @return list of types has been converted from map to list
     */
    private List<Type> convertMapToList(List<Object[]> inputList, int code) {
        if (inputList.isEmpty())
            return null;
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

    private boolean isNotFound(Integer id) {
        Optional<Type> hostelType = typeRepository.findById(id);
        return hostelType.isEmpty();
    }
}
