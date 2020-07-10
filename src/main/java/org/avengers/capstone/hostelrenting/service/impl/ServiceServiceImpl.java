package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Service;
import org.avengers.capstone.hostelrenting.repository.ServiceRepository;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    private ServiceRepository serviceRepository;

    @Autowired
    public void setServiceRepository(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public Service save(Service service) {
        if (serviceRepository.getByServiceName(service.getServiceName()) != null) {
            throw new DuplicateKeyException(String.format(Constant.Message.DUPLICATED_ERROR, "service_name", service.getServiceName()));
        }
        return serviceRepository.save(service);
    }

    @Override
    public Service findById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Service.class, "id", id.toString());
        }
        return serviceRepository.getOne(id);
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        if (isNotFound(id)) {
            throw new EntityNotFoundException(Service.class, "id", id.toString());
        }
        serviceRepository.deleteById(id);
    }

    @Override
    public long getCount() {
        return serviceRepository.count();
    }

    private boolean isNotFound(Integer id) {
        Optional<Service> service = serviceRepository.findById(id);
        return service.isEmpty();
    }
}
