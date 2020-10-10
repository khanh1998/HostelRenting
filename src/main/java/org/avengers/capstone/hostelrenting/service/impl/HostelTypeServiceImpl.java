package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.repository.TypeRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
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
public class HostelTypeServiceImpl implements HostelTypeService {
    @Autowired
    private TypeRepository typeRepository;
    private HostelGroupService hostelGroupService;

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
    }

//    @Autowired
//    public void setHostelTypeRepository(TypeRepository typeRepository) {
//        this.typeRepository = typeRepository;
//    }

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
        hostelGroupService.checkExist(hostelGroupId);
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
        Collection<Type> locTypes = new ArrayList<>();
        Collection<Type> schoolMateTypes = new ArrayList<>();
        Collection<Type> compatriotTypes = new ArrayList<>();
        if (latitude != null && longitude != null) {
            // if long&lat != null ==> get surroundings
            locTypes = typeRepository.getSurroundings(latitude, longitude, distance, pageable);
        } else {
            //default get ...
            locTypes = typeRepository.findAll(pageable).toList();
        }

        if (schoolId != null) {
            schoolMateTypes = convertMapToList(typeRepository.getBySchoolMates(schoolId), 1);
        }

        if (provinceId != null) {
            compatriotTypes = convertMapToList(typeRepository.getByCompatriot(provinceId), 2);
        }

        // new collection to retainAll (unmodifiable collection cannot be removed)
        Collection temp = new ArrayList(locTypes);
        if (!compatriotTypes.isEmpty() || !schoolMateTypes.isEmpty()) {
            temp.retainAll(Stream.concat(compatriotTypes.stream(), schoolMateTypes.stream()).collect(Collectors.toList()));
        }

        return temp;
    }

    /**
     * @param inputList
     * @param code      == 1 is schoolmate, otherwise is compatriot
     * @return
     */
    public List<Type> convertMapToList(List<Object[]> inputList, int code) {
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
