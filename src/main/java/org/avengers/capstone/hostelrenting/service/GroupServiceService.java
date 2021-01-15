package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.groupService.GroupServiceDTOCreateForGroup;
import org.avengers.capstone.hostelrenting.model.GroupService;

import java.util.List;

/**
 * @author duattt on 10/16/20
 * @created 16/10/2020 - 15:05
 * @project youthhostelapp
 */
public interface GroupServiceService {
    void checkExist(Integer id);
    GroupService findById(Integer id);
    GroupService create(GroupServiceDTOCreateForGroup dto, int groupId);
    GroupService update(GroupServiceDTOCreateForGroup dto, int groupId);
    void delete(int groupServiceId);
    List<GroupService> getByGroupId(int groupId);
}
