package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.repository.GroupRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostelGroupServiceImpl implements HostelGroupService {
    private GroupRepository groupRepository;
    private VendorService vendorService;
    private static final Logger logger = LoggerFactory.getLogger(HostelGroupServiceImpl.class);

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
        // Set createdAt
        reqModel.setCreatedAt(System.currentTimeMillis());

        return groupRepository.save(reqModel);
    }

    @Override
    public Group update(Group reqModel) {
        // Set updatedAt
        reqModel.setUpdatedAt(System.currentTimeMillis());

        return groupRepository.save(reqModel);
    }

    @Override
    public Collection<Group> getByVendorId(Long vendorId, int size, int page) {
        Vendor existedModel = vendorService.findById(vendorId);
        return existedModel.getGroups()
                .stream()
                .skip(size*page)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        //TODO: Implement
    }
}
