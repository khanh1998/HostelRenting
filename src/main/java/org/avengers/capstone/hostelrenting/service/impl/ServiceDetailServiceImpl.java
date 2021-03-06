package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.model.GroupService;
import org.avengers.capstone.hostelrenting.repository.ServiceDetailRepository;
import org.avengers.capstone.hostelrenting.service.ServiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author duattt on 9/26/20
 * @created 26/09/2020 - 20:13
 * @project youthhostelapp
 */
@Service
public class ServiceDetailServiceImpl implements ServiceDetailService {
    private ServiceDetailRepository serviceDetailRepository;

    @Autowired
    public void setServiceDetailRepository(ServiceDetailRepository serviceDetailRepository) {
        this.serviceDetailRepository = serviceDetailRepository;
    }

    @Override
    public GroupService create(GroupService groupService) {
        //TODO: check duplicate
        return serviceDetailRepository.save(groupService);
    }
}
