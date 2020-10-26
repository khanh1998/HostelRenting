package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.model.Group;

import java.util.Collection;

public interface GroupService {
    void checkExist(Integer id);
    Group findById(Integer id);
    Group create(Group reqModel);
    Group update(Group reqModel);
    Collection<Group> getByVendorId(Long vendorId, int size, int page);
    void deleteById(Integer id);
}
