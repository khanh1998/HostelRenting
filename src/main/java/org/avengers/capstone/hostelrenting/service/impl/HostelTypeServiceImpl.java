package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.repository.HostelTypeRepository;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<HostelType> findAllHostelType() {
        return hostelTypeRepository.findAll();
    }

    @Override
    public Optional<HostelType> findHostelTypeById(Integer id) {
        return hostelTypeRepository.findById(id);
    }

    @Override
    public HostelType findHostelTypeByHostelTypeId(Integer hostelTypeId) {
        Optional<HostelType> hostelType = hostelTypeRepository.findById(hostelTypeId);
        if (hostelType.isEmpty()){
            throw new EntityNotFoundException(Province.class, "id", hostelTypeId.toString());
        }else{
            return hostelType.get();
        }
    }

    @Override
    public List<HostelType> findAllHostelTypeByHostelGroupId(Integer hostelGroupId) {
        List<HostelType> hostelType = hostelTypeRepository.findAllHostelTypeByHostelGroupId(hostelGroupId);
        if (hostelType.isEmpty()){
            throw new EntityNotFoundException(Province.class, "id", hostelGroupId.toString());
        }else{
            return hostelType;
        }
    }

    @Override
    public HostelType saveHostelType(HostelType hostelType) {
        return hostelTypeRepository.save(hostelType);
    }

    @Override
    public void removeHostelType(HostelType hostelType) {
        hostelTypeRepository.delete(hostelType);
    }
}
