package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.ServiceDTO;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Category;
import org.avengers.capstone.hostelrenting.model.Service;
import org.avengers.capstone.hostelrenting.repository.ServiceRepository;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    private ServiceRepository serviceRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Autowired
    public void setServiceRepository(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Check that given id is existed or not
     *
     * @param id the given id
     */
    @Override
    public void checkExist(Integer id) {
        Optional<Service> model = serviceRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Service.class, "id", id.toString());
    }

    /**
     * Get all services
     *
     * @return list of DTO
     */
    @Override
    public List<ServiceDTO> getAll() {
        return serviceRepository.findAll()
                .stream()
                .map(service -> modelMapper.map(service, ServiceDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Find service by given id
     *
     * @param id the given id
     * @return service model
     */
    @Override
    public Service findById(Integer id) {
        checkExist(id);

        return serviceRepository.getOne(id);
    }
}
