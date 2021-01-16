package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOUpdate;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.repository.GroupRepository;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;
    private ManagerService managerService;
    private StreetWardService streetWardService;
    private StreetService streetService;

    @Autowired
    public void setStreetWardService(StreetWardService streetWardService) {
        this.streetWardService = streetWardService;
    }

    @Autowired
    public void setStreetService(StreetService streetService) {
        this.streetService = streetService;
    }

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
        return groupRepository.getOne(id);
    }

    @Override
    public Group create(Group reqModel) {
        //TODO: check duplicate

        return groupRepository.save(reqModel);
    }

    @Override
    public Group update(GroupDTOUpdate reqDTO, Integer groupId) {
        Group existedModel = this.findById(groupId);
        // update manager
        if (reqDTO.getManagerId() != null && reqDTO.getManagerId() != existedModel.getManager().getManagerId()) {
            Manager newManager = managerService.findById(reqDTO.getManagerId());
            existedModel.setManager(newManager);
        }
        reqDTO.setManagerId(null);
        // set address object
        Street street = Street.builder()
                .streetName(reqDTO.getAddressFull().getStreetName())
                .build();
        // create street and save streetward if not exist
        StreetWard address = streetWardService.findByStreetIdAndWardId(
                streetService.createIfNotExist(street).getStreetId(),
                reqDTO.getAddressFull().getWardId()
        );
        existedModel.setAddress(address);
        reqDTO.setAddressFull(null);
        modelMapper.map(reqDTO, existedModel);
        return groupRepository.save(existedModel);
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
