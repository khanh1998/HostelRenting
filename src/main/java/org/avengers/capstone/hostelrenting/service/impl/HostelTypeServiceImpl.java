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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<HostelType> findAll(Pageable pageable) {
        return hostelTypeRepository.findAll(pageable).toList();
    }

    @Override
    public HostelType save(HostelType hostelType) {
        //TODO: Check duplicate object
        if (hostelTypeRepository.equals(hostelType)) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "all", "all"));
        }

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
     * @param hostelGroupId
     * @return HostelTypes of given group, otherwise null
     */
    @Override
    public List<HostelType> findByHostelGroupId(Integer hostelGroupId) {
        List<HostelType> hostelTypes = hostelTypeRepository.findByHostelGroup_GroupId((hostelGroupId));
        return hostelTypes;
    }

    /**
     * Get list of HostelTypes around the given location and distance
     *
     * @param latitude
     * @param longitude
     * @param distance - radius of circle from the search point
     * @param size
     * @param page
     * @return
     */
    @Override
    public List<HostelType> findByLocationAndDistance(Double latitude, Double longitude, Double distance, int size, int page) {
        Pageable pageable = PageRequest.of(page-1, size);
        List<HostelType> types;
        if (latitude != null && longitude != null){
            types = hostelTypeRepository.getSurroundings(latitude, longitude, distance);
        }else{
            //TODO: get all hotest hostel types
            types = findAll(pageable);
        }
        return types;
    }

    private boolean isNotFound(Integer id) {
        Optional<HostelType> hostelType = hostelTypeRepository.findById(id);
        return hostelType.isEmpty();
    }
}
