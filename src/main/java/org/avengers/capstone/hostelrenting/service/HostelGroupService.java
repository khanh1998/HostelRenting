package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.model.HostelGroup;

import java.util.List;
import java.util.Optional;

public interface HostelGroupService {
    Optional<HostelGroup> findAllHostelGroupById(Integer id);
    HostelGroup findHostelGroupByHostelGroupId(Integer hostelGroupId);
    List<HostelGroupDTO> findAllHostelGroup();
//    List<HostelGroup> findAllHostelGroupByScheduleId(Integer scheduleId);
    HostelGroup saveHostelGroup(HostelGroup hostelGroup);
    void removeHostelGroup(Integer hostelGroupId);
}
