package org.avengers.capstone.hostelrenting.service;

import org.apache.catalina.Host;
import org.avengers.capstone.hostelrenting.model.HostelGroup;

import java.util.List;

public interface HostelGroupService {
    HostelGroup findById(Integer id);
    List<HostelGroup> findAll();
    HostelGroup save(HostelGroup hostelGroup);
    void deleteById(Integer id);

    List<HostelGroup> getSurroundings(Double latitude, Double longitude);
}
