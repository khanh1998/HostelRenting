package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.repository.HostelGroupRepository;
import org.avengers.capstone.hostelrenting.service.HostelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HostelGroupServiceImpl implements HostelGroupService {
    @Autowired
    private HostelGroupRepository hostelGroupRepository;

    @Override
    public Optional<HostelGroup> findAllHostelGroupById(Integer id) {
        return hostelGroupRepository.findById(id);
    }

    @Override
    public HostelGroup findHostelGroupById(Integer hostelGroupId) {
        return hostelGroupRepository.findById(hostelGroupId).get();
    }

    @Override
    public List<HostelGroupDTO> getAllHostelGroup() {
        List<HostelGroup> hostelGroupList = hostelGroupRepository.findAll();
        List<HostelGroupDTO> hostelGroupDTOList = hostelGroupList.stream().map(item -> {
            HostelGroupDTO dto = new HostelGroupDTO();
            dto.setHostelGroupId(item.getHostelGroupId());
            dto.setHostelGroupName(item.getHostelGroupName());
            dto.setDetailedAddress(item.getDetailedAddress());
            dto.setLatitude(item.getLatitude());
            dto.setLongitude(item.getLongitude());
            dto.setVendorId(item.getVendor().getVendorId());
            return dto;
        }).collect(Collectors.toList());
        return hostelGroupDTOList;
    }

    @Override
    public List<HostelGroup> findAllHostelGroupByVendorId(Integer vendorId) {
        return hostelGroupRepository.findAllHostelGroupByVendorId(vendorId);
    }

    @Override
    public HostelGroup saveHostelGroup(HostelGroup hostelGroup) {
        return hostelGroupRepository.save(hostelGroup);
    }

    @Override
    public void removeHostelGroup(HostelGroup hostelGroup) {
        hostelGroupRepository.delete(hostelGroup);
    }
}
