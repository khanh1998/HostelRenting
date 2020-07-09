package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.repository.HostelTypeRepository;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostelTypeServiceImpl implements HostelTypeService {
    HostelTypeRepository hostelTypeRepository;

    @Autowired
    public void setHostelTypeRepository(HostelTypeRepository hostelTypeRepository) {
        this.hostelTypeRepository = hostelTypeRepository;
    }

    @Override
    public HostelType findById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(HostelType.class, "id", id.toString());
        }

        return hostelTypeRepository.getOne(id);
    }

    @Override
    public List<HostelType> findAll() {
        return hostelTypeRepository.findAll();
    }

    @Override
    public HostelType save(HostelType hostelType) {
        if (hostelTypeRepository.equals(hostelType)){
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "all", "all"));
        }

        return hostelTypeRepository.save(hostelType);
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)){
            throw new EntityNotFoundException(HostelType.class, "id", id.toString());
        }

        hostelTypeRepository.deleteById(id);
    }

    @Override
    public HostelType findByIdAndHostelGroupId(Integer hostelTypeId, Integer hostelGroupId) {
        Optional<HostelType> hostelType = hostelTypeRepository.findByTypeIdAndHostelGroup_GroupId(hostelTypeId, hostelGroupId);
        if (hostelType.isEmpty()){
            throw new EntityNotFoundException(HostelType.class, "hostel_type_id", hostelTypeId.toString(), "hostel_group_id", hostelGroupId.toString());
        }

        return hostelType.get();
    }

    @Override
    public List<HostelType> findByHostelGroupId(Integer hostelGroupId) {
        List<HostelType> hostelTypes = hostelTypeRepository.findByHostelGroup_GroupId((hostelGroupId));
        if (hostelTypes.isEmpty()){
            throw new EntityNotFoundException(HostelType.class, "hostel_group_id", hostelGroupId.toString());
        }

        return hostelTypes;
    }

    private boolean isNotFound(Integer id) {
        Optional<HostelType> hostelType = hostelTypeRepository.findById(id);
        return hostelType.isEmpty();
    }
}
