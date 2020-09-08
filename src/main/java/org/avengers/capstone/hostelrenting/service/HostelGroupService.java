package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.HostelGroup;

public interface HostelGroupService {
    void checkExist(Integer id);
    HostelGroup findById(Integer id);
    HostelGroup create(HostelGroup reqModel);
    HostelGroup update(HostelGroup reqModel);
    void delete(Integer id);
}
