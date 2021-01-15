package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.group.GroupDTOUpdate;
import org.avengers.capstone.hostelrenting.model.Group;

import java.util.Collection;
import java.util.UUID;

public interface GroupService {
    void checkExist(Integer id);
    Group findById(Integer id);
    Group create(Group reqModel);
    Group update(GroupDTOUpdate reqModel, Integer groupId);
    Group save(Group reqModel);
    Collection<Group> getByVendorId(UUID vendorId, int size, int page);
    void deleteById(Integer id);
}
