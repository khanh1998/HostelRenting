package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.TypeService;
import org.avengers.capstone.hostelrenting.repository.HostelTypeServiceRepository;
import org.avengers.capstone.hostelrenting.service.HostelTypeServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelTypeServiceServiceImpl implements HostelTypeServiceService {
    HostelTypeServiceRepository hostelTypeServiceRepository;

    @Autowired
    public void setHostelTypeServiceRepository(HostelTypeServiceRepository hostelTypeServiceRepository) {
        this.hostelTypeServiceRepository = hostelTypeServiceRepository;
    }

    @Override
    public List<TypeService> findByHostelTypeId(Integer id) {
        List<TypeService> typeServices = hostelTypeServiceRepository.findByHostelType_TypeId(id);
        return typeServices;
    }

    @Override
    public List<TypeService> save(List<TypeService> typeServices) {
        return hostelTypeServiceRepository.saveAll(typeServices);
    }
}
