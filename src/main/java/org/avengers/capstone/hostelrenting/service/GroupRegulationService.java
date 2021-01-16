package org.avengers.capstone.hostelrenting.service;

import org.avengers.capstone.hostelrenting.dto.groupRegulation.GroupRegulationDTOResponse;

import java.util.List;

public interface GroupRegulationService {
    GroupRegulationDTOResponse create(Integer regulationId, Integer groupId);

    void delete(Integer groupRegulationId);

    List<GroupRegulationDTOResponse> getByGroupId(Integer groupId);
}
