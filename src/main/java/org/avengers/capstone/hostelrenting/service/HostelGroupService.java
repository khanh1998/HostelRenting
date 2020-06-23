package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.model.HostelGroup;

import java.util.List;
import java.util.Optional;

public interface HostelGroupService {
    Optional<HostelGroup> findAllHostelGroupById(Integer id);
    HostelGroup findHostelGroupById(Integer hostelGroupId);
    List<HostelGroupDTO> getAllHostelGroup();
    List<HostelGroup> findAllHostelGroupByVendorId(Integer vendorId);
    HostelGroup saveHostelGroup(HostelGroup hostelGroup);
    void removeHostelGroup(HostelGroup hostelGroup);
}
