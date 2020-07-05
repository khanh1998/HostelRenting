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
    private HostelGroupRepository hostelGroupRepository;

    @Autowired
    public void setHostelGroupRepository(HostelGroupRepository hostelGroupRepository) {
        this.hostelGroupRepository = hostelGroupRepository;
    }

    @Override
    public Optional<HostelGroup> findAllHostelGroupById(Integer id) {
        return hostelGroupRepository.findById(id);
    }

    @Override
    public HostelGroup findHostelGroupByHostelGroupId(Integer hostelGroupId) {
        return hostelGroupRepository.findById(hostelGroupId).get();
    }

    @Override
    public List<HostelGroupDTO> findAllHostelGroup() {
        List<HostelGroup> hostelGroupList = hostelGroupRepository.findAll();
        List<HostelGroupDTO> hostelGroupDTOList = hostelGroupList.stream().map(item -> {
            HostelGroupDTO dto = new HostelGroupDTO();
            dto.setHostelGroupId(item.getHostelGroupId());
            dto.setHostelGroupName(item.getHostelGroupName());
            dto.setDetailedAddress(item.getDetailedAddress());
            dto.setLatitude(item.getLatitude());
            dto.setLongitude(item.getLongitude());
            dto.setDistrictId(item.getDistrict().getDistrictId());
//            List<Integer> scheduleOnlyIdDTOS = item.getHostelGroupSchedules().stream().map(item2 -> item2.getSchedule().getScheduleId()).collect(Collectors.toList());
//            dto.setScheduleIds(scheduleOnlyIdDTOS);
            return dto;
        }).collect(Collectors.toList());
        return hostelGroupDTOList;
    }

//    @Override
//    public List<HostelGroup> findAllHostelGroupByScheduleId(Integer scheduleId) {
////        return hostelGroupRepository.findAllBySchedules_ScheduleId(scheduleId);
//        return null;
//    }

    @Override
    public HostelGroup saveHostelGroup(HostelGroup hostelGroup) {
        return hostelGroupRepository.save(hostelGroup);
    }

    @Override
    public void removeHostelGroup(Integer hostelGroupId) {
        hostelGroupRepository.deleteById(hostelGroupId);
    }
}
