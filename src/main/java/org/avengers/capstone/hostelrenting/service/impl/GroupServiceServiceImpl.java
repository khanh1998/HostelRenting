package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.groupService.GroupServiceDTOCreateForGroup;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.repository.GroupServiceRepository;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.GroupServiceService;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duattt on 10/16/20
 * @created 16/10/2020 - 15:08
 * @project youthhostelapp
 */
@Service
public class GroupServiceServiceImpl implements GroupServiceService {
    private GroupServiceRepository groupServiceRepository;
    private ServiceService serviceService;
    private org.avengers.capstone.hostelrenting.service.GroupService groupServices;
    private ModelMapper modelMapper;

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setGroupServiceService(org.avengers.capstone.hostelrenting.service.GroupService groupService) {
        this.groupServices = groupService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setGroupServiceRepository(GroupServiceRepository groupServiceRepository) {
        this.groupServiceRepository = groupServiceRepository;
    }

    @Override
    public void checkExist(Integer id) {
        Optional<org.avengers.capstone.hostelrenting.model.GroupService> model = groupServiceRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(GroupService.class, "id", id.toString());
    }

    @Override
    public org.avengers.capstone.hostelrenting.model.GroupService findById(Integer id) {
        checkExist(id);
        return groupServiceRepository.getOne(id);
    }

    @Override
    public org.avengers.capstone.hostelrenting.model.GroupService create(GroupServiceDTOCreateForGroup dto, int groupId) {
        org.avengers.capstone.hostelrenting.model.GroupService groupService =
                modelMapper.map(dto, org.avengers.capstone.hostelrenting.model.GroupService.class);
        Group group = groupServices.findById(groupId);
        org.avengers.capstone.hostelrenting.model.Service service =
                serviceService.findById(dto.getServiceId());
        groupService.setGroup(group);
        groupService.setService(service);
        groupService.setGroupServiceId(0);
        return groupServiceRepository.save(groupService);
    }

    @Override
    public org.avengers.capstone.hostelrenting.model.GroupService update(GroupServiceDTOCreateForGroup dto, int groupId) {
        org.avengers.capstone.hostelrenting.model.GroupService groupService = findById(dto.getGroupServiceId());
        groupService.setActive(dto.getIsActive());
        groupService.setRequired(dto.getIsRequired());
        groupService.setPrice(dto.getPrice());
        groupService.setUserUnit(dto.getUserUnit());
        groupService.setPriceUnit(dto.getPriceUnit());
        groupService.setTimeUnit(dto.getTimeUnit());
        return groupServiceRepository.save(groupService);
    }

    @Override
    public void delete(int groupServiceId) {
        org.avengers.capstone.hostelrenting.model.GroupService groupService = findById(groupServiceId);
        groupServiceRepository.delete(groupService);
    }

    @Override
    public List<org.avengers.capstone.hostelrenting.model.GroupService> getByGroupId(int groupId) {
        return new ArrayList<>(groupServiceRepository.findByGroup_GroupId(groupId));
    }
}
