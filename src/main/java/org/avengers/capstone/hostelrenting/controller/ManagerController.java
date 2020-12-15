package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.manager.ManagerDTOCreate;
import org.avengers.capstone.hostelrenting.dto.manager.ManagerDTOResponse;
import org.avengers.capstone.hostelrenting.dto.manager.ManagerDTOUpdate;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Group;
import org.avengers.capstone.hostelrenting.model.Manager;
import org.avengers.capstone.hostelrenting.repository.GroupRepository;
import org.avengers.capstone.hostelrenting.repository.ManagerRepository;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.ManagerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author duattt on 14/12/2020
 * @created 14/12/2020 - 11:33
 * @project youthhostelapp
 */
@RestController
@RequestMapping("/api/v1")
public class ManagerController {
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);
    private ManagerService managerService;
    private GroupService groupService;
    private GroupRepository groupRepository;
    private ManagerRepository managerRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setManagerRepository(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/groups/{groupId}/managers")
    public ResponseEntity<?> createManager(@Valid @RequestBody ManagerDTOCreate reqDTO,
                                           @PathVariable Integer groupId) {
        //log start update
        logger.info("START - creating manager");
        Group exGroup = groupService.findById(groupId);
        if (exGroup.getManager() != null && exGroup.getManager().getManagerPhone().equals(reqDTO.getManagerPhone()))
            throw new GenericException(Manager.class, "existed in your group", "managerPhone", reqDTO.getManagerPhone(), "groupId", String.valueOf(exGroup.getGroupId()));
        Manager reqModel = modelMapper.map(reqDTO, Manager.class);
        Manager resModel = managerService.createNewManager(reqModel);
        exGroup.setManager(resModel);
        groupRepository.save(exGroup);
        ManagerDTOResponse resDTO = modelMapper.map(resModel, ManagerDTOResponse.class);

        logger.info("SUCCESSFUL - creating manager");

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your manager has been created successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @DeleteMapping("/groups/{groupId}/managers")
    public ResponseEntity<?> removeManager(@PathVariable Integer groupId) {
        //log start update
        logger.info("START - removing manager");
        Group exGroup = groupService.findById(groupId);
        Manager exManager = exGroup.getManager();
        exGroup.setManager(null);
        groupRepository.save(exGroup);
        logger.info("SUCCESSFUL - removing manager");

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(null, "Your manager has been deleted successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/managers/{managerId}")
    public ResponseEntity<?> updateManager(@RequestBody ManagerDTOUpdate reqDTO,
                                           @PathVariable UUID managerId) {
        //log start update
        logger.info("START - change manager status");
        Manager exManager = managerService.findById(managerId);
        modelMapper.map(reqDTO, exManager);
        managerRepository.save(exManager);
        logger.info("SUCCESSFUL - change manager status");

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(null, "Your manager has been changed successfully!");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
