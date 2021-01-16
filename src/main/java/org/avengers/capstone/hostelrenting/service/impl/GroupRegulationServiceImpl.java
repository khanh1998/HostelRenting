package org.avengers.capstone.hostelrenting.service.impl;

import org.avengers.capstone.hostelrenting.dto.groupRegulation.GroupRegulationDTOResponse;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.GroupRegulation;
import org.avengers.capstone.hostelrenting.model.Regulation;
import org.avengers.capstone.hostelrenting.repository.GroupRegulationRepository;
import org.avengers.capstone.hostelrenting.service.GroupRegulationService;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.RegulationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupRegulationServiceImpl implements GroupRegulationService {
    private GroupRegulationRepository groupRegulationRepository;
    private GroupService groupService;
    private RegulationService regulationService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setRegulationService(RegulationService regulationService) {
        this.regulationService = regulationService;
    }

    @Autowired
    public void setGroupRegulationRepository(GroupRegulationRepository groupRegulationRepository) {
        this.groupRegulationRepository = groupRegulationRepository;
    }

    @Override
    public GroupRegulationDTOResponse create(Integer regulationId, Integer groupId) {
        GroupRegulation regulation = new GroupRegulation();
        Regulation regu = regulationService.findById(regulationId);
        System.out.println(regu);
        regulation.setRegulation(regu);
        Group group = groupService.findById(groupId);
        System.out.println(group);
        regulation.setGroup(group);
        GroupRegulation saved = groupRegulationRepository.save(regulation);
        System.out.println(saved);
        return modelMapper.map(saved, GroupRegulationDTOResponse.class);
    }

    @Override
    public void delete(Integer groupRegulationId) {
        groupRegulationRepository.deleteById(groupRegulationId);
    }

    @Override
    public List<GroupRegulationDTOResponse> getByGroupId(Integer groupId) {
        return groupRegulationRepository.findByGroup_GroupId(groupId)
                .stream()
                .map(model -> modelMapper.map(model, GroupRegulationDTOResponse.class))
                .collect(Collectors.toList());
    }
}
