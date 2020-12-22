package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.GroupRegulation;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.GroupRegulationRepository;
import org.avengers.capstone.hostelrenting.repository.GroupRepository;
import org.avengers.capstone.hostelrenting.repository.GroupServiceRepository;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private VendorService vendorService;
    private GroupRegulationRepository groupRegulationRepository;
    private GroupServiceRepository groupServiceRepository;

    @Autowired
    public void setGroupServiceRepository(GroupServiceRepository groupServiceRepository) {
        this.groupServiceRepository = groupServiceRepository;
    }

    @Autowired
    public void setGroupRegulationRepository(GroupRegulationRepository groupRegulationRepository) {
        this.groupRegulationRepository = groupRegulationRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Override
    public void checkExist(Integer id) {
        Optional<Group> model = groupRepository.findById(id);
        if (model.isEmpty())
            throw new EntityNotFoundException(Group.class, "id", id.toString());
    }

    @Override
    public Group findById(Integer id) {
        checkExist(id);
        Group resModel = groupRepository.getOne(id);
//        handleObjForEndUser(resModel);
        return resModel;
    }

    @Override
    public Group create(Group reqModel) {
        //TODO: check duplicate

        return groupRepository.save(reqModel);
    }

    @Override
    public Group update(Group reqModel) {

        return groupRepository.save(reqModel);
    }

    @Override
    public Group save(Group reqModel) {
        return groupRepository.save(reqModel);
    }

    @Override
    public Collection<Group> getByVendorId(UUID vendorId, int size, int page) {
        Vendor existedModel = vendorService.findById(vendorId);
        return existedModel.getGroups()
                .stream()
                .skip(size * page)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        //TODO: Implement
    }

}
