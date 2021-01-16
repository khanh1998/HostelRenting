package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.groupRegulation.GroupRegulationDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.GroupRegulationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/regulations")
public class GroupRegulationController {
    private GroupRegulationService groupRegulationService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setGroupRegulationService(GroupRegulationService groupRegulationService) {
        this.groupRegulationService = groupRegulationService;
    }

    @GetMapping
    ResponseEntity<?> getRegulationsOfGroup(@PathVariable Integer groupId) {
        List<GroupRegulationDTOResponse> res = groupRegulationService.getByGroupId(groupId);
        String message = "Regulations of group is retrieved successfully";
        ApiSuccess<List<GroupRegulationDTOResponse>> apiSuccess = new ApiSuccess(res, message);
        return ResponseEntity.ok(apiSuccess);
    }

    @PostMapping
    ResponseEntity<?> createRegulationsForGroup(@RequestParam List<Integer> groupRegulationIds, @PathVariable Integer groupId) {
        List<GroupRegulationDTOResponse> res = groupRegulationIds.stream()
                .map(regulationId -> groupRegulationService.create(regulationId, groupId))
                .collect(Collectors.toList());
        String message = "Regulations are created successfully";
        return ResponseEntity.ok(new ApiSuccess<>(res, message));
    }

    @DeleteMapping
    ResponseEntity<?> deleteRegulationsForGroup(@RequestParam List<Integer> ids) {
        ids.forEach(id -> groupRegulationService.delete(id));
        String message = "regulations of group are deleted successfully";
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(null, message);
        return ResponseEntity.ok(apiSuccess);
    }
}
