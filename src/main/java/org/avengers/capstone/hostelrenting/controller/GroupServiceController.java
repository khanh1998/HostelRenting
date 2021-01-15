package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.groupService.GroupServiceDTOCreate;
import org.avengers.capstone.hostelrenting.dto.groupService.GroupServiceDTOCreateForGroup;
import org.avengers.capstone.hostelrenting.dto.groupService.GroupServiceDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.GroupServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groups/{groupId}")
public class GroupServiceController {
    private GroupServiceService groupServiceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setGroupServiceService(GroupServiceService groupServiceService) {
        this.groupServiceService = groupServiceService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/services")
    ResponseEntity<?> getServicesOfGroup(@PathVariable Integer groupId) {
        List<GroupServiceDTOResponse> list = groupServiceService.getByGroupId(groupId).stream()
                .map(item -> modelMapper.map(item, GroupServiceDTOResponse.class))
                .collect(Collectors.toList());
        ApiSuccess<List<GroupServiceDTOResponse>> apiSuccess = new ApiSuccess<>(list);
        return ResponseEntity.ok(apiSuccess);
    }

    @PostMapping("/services")
    ResponseEntity<?> createServicesOfGroup(@RequestBody List<GroupServiceDTOCreateForGroup> dtos, @PathVariable Integer groupId) {
        List<GroupServiceDTOResponse> created = dtos.stream()
                .map(dto -> groupServiceService.create(dto, groupId))
                .map(model -> modelMapper.map(model, GroupServiceDTOResponse.class))
                .collect(Collectors.toList());
        ApiSuccess<List<GroupServiceDTOResponse>> apiSuccess = new ApiSuccess<>(created);
        return ResponseEntity.ok(apiSuccess);
    }

    @PutMapping("/services")
    ResponseEntity<?> updateServicesOfGroup(@RequestBody List<GroupServiceDTOCreateForGroup> dtos, @PathVariable Integer groupId) {
        System.out.println(dtos);
        System.out.println(groupId);
        List<GroupServiceDTOResponse> updated = dtos.stream()
                .map(dto -> groupServiceService.update(dto, groupId))
                .map(model -> modelMapper.map(model, GroupServiceDTOResponse.class))
                .collect(Collectors.toList());
        ApiSuccess<List<GroupServiceDTOResponse>> apiSuccess = new ApiSuccess<>(updated);
        return ResponseEntity.ok(apiSuccess);
    }

    @DeleteMapping("/services")
    ResponseEntity<?> deleteServicesOfGroup(@RequestParam List<Integer> ids) {
        ids.forEach(id -> {
            groupServiceService.delete(id);
        });
        String message = "Services of group are deleted";
        ApiSuccess<List<GroupServiceDTOResponse>> apiSuccess = new ApiSuccess<>(null, message);
        return ResponseEntity.ok(apiSuccess);
    }
}
