package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.repository.HostelTypeRepository;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private HostelTypeRepository hostelTypeRepository;

    @Autowired
    public void setHostelTypeRepository(HostelTypeRepository hostelTypeRepository) {
        this.hostelTypeRepository = hostelTypeRepository;
    }

    @Override
    public HostelType findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelType.class, "id", id.toString());
        }

        return hostelTypeRepository.getOne(id);
    }

    @Override
    public HostelType save(HostelType hostelType) {
        //TODO: Check duplicate object
        if (hostelTypeRepository.equals(hostelType)) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "all", "all"));
        }
        // Set createdAt
        hostelType.setCreatedAt(System.currentTimeMillis());

        return hostelTypeRepository.save(hostelType);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(HostelType.class, "id", id.toString());
        }

        hostelTypeRepository.deleteById(id);
    }

    /**
     * Get all Types by groupId
     *
     * @param hostelGroupId
     * @return HostelTypes of given group, otherwise null
     */
    @Override
    public List<HostelType> findByHostelGroupId(Integer hostelGroupId) {
        List<HostelType> hostelTypes = hostelTypeRepository.findByHostelGroup_GroupId((hostelGroupId));
        return hostelTypes;
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
    public Collection<HostelType> searchWithMainFactors(Double latitude, Double longitude, Double distance, Integer schoolId, Integer provinceId, String sortBy, Boolean asc, int size, int page) {

        Sort sort = Sort.by(asc == true ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Collection<HostelType> locTypes = new ArrayList<>();
        Collection<HostelType> schoolMateTypes = new ArrayList<>();
        Collection<HostelType> compatriotTypes = new ArrayList<>();
        if (latitude != null && longitude != null) {
            // if long&lat != null ==> get surroundings
            locTypes = hostelTypeRepository.getSurroundings(latitude, longitude, distance, pageable);
        }else {
            //default get ...
            locTypes = hostelTypeRepository.findAll(pageable).toList();
        }

        if (schoolId != null) {
            schoolMateTypes = convertMapToList(hostelTypeRepository.getBySchoolMates(schoolId), 1);
        }

        if (provinceId != null) {
            compatriotTypes = convertMapToList(hostelTypeRepository.getByCompatriot(provinceId), 2);
        }

        // new collection to retainAll (unmodifiable collection cannot be removed)
        Collection temp = new ArrayList(locTypes);
        if (!compatriotTypes.isEmpty() || !schoolMateTypes.isEmpty()){
            temp.retainAll(Stream.concat(compatriotTypes.stream(), schoolMateTypes.stream()).collect(Collectors.toList()));
        }

        return temp;
    }

    /**
     * @param inputList
     * @param code     == 1 is schoolmate, otherwise is compatriot
     * @return
     */
    public List<HostelType> convertMapToList(List<Object[]> inputList, int code) {
        if (inputList.isEmpty())
            return null;
        Map<Integer, Integer> inputMap = new HashMap<>();
        for (Object[] obj : inputList) {
            Integer typeId = (Integer) obj[0];
            Integer count = ((BigInteger) obj[1]).intValue();
            inputMap.put(typeId, count);
        }

        List<HostelType> hostelTypes = inputMap.keySet().stream()
                .map(integer -> {
                    HostelType temp = findById(integer);
                    if (code == 1)
                        temp.setSchoolmate(Math.toIntExact(inputMap.get(integer)));
                    else
                        temp.setCompatriot(Math.toIntExact(inputMap.get(integer)));
                    return temp;
                }).collect(Collectors.toList());
        return hostelTypes;
    }

    private boolean isNotFound(Integer id) {
        Optional<HostelType> hostelType = hostelTypeRepository.findById(id);
        return hostelType.isEmpty();
    }
}
