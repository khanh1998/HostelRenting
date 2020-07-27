package org.avengers.capstone.hostelrenting.service;

import org.apache.catalina.Host;
import org.avengers.capstone.hostelrenting.model.HostelGroup;
import org.avengers.capstone.hostelrenting.model.HostelType;

import java.util.List;

public interface HostelGroupService {
    HostelGroup findById(Integer id);
    List<HostelGroup> findAll();
    HostelGroup save(HostelGroup hostelGroup);
    void deleteById(Integer id);
}
