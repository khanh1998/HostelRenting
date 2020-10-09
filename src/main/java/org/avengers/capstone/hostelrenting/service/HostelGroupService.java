package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Group;

public interface HostelGroupService {
    void checkExist(Integer id);
    Group findById(Integer id);
    Group create(Group reqModel);
    Group update(Group reqModel);
    void deleteById(Integer id);
}
