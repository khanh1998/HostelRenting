package capstone.tdhostel.controller;

import capstone.tdhostel.dto.HostelGroupDTO;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.HostelGroup;
import capstone.tdhostel.service.HostelGroupService;
import capstone.tdhostel.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HostelGroupController {
    @Autowired
    private HostelGroupService hostelGroupService;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/groups")
    public List<HostelGroupDTO> getAllHostelGroup() {
        return hostelGroupService.getAllHostelGroup();
    }

    @GetMapping("/groups/{id}")
    public HostelGroupDTO getHostelGroupById(@PathVariable int id) {
        return hostelGroupService.findAllHostelGroupById(id).map(hostelGroup -> {
            HostelGroupDTO dto = new HostelGroupDTO();
            dto.setHostelGroupId(id);
            dto.setHostelGroupName(hostelGroup.getHostelGroupName());
            dto.setDetailedAddress(hostelGroup.getDetailedAddress());
            dto.setLatitude(hostelGroup.getLatitude());
            dto.setLongitude(hostelGroup.getLongitude());
            dto.setVendorId(hostelGroup.getVendor().getVendorId());
            return dto;
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Group not found with id "+ id));
    }

    @GetMapping("/groups/vendor/{vendorId}")
    public List<HostelGroupDTO> getHostelGroupByVendorId(@PathVariable int vendorId) {
        return hostelGroupService.findAllHostelGroupByVendorId(vendorId).stream().map(hostelGroup -> {
            HostelGroupDTO dto = new HostelGroupDTO();
            dto.setHostelGroupId(hostelGroup.getHostelGroupId());
            dto.setHostelGroupName(hostelGroup.getHostelGroupName());
            dto.setDetailedAddress(hostelGroup.getDetailedAddress());
            dto.setLatitude(hostelGroup.getLatitude());
            dto.setLongitude(hostelGroup.getLongitude());
            dto.setVendorId(hostelGroup.getVendor().getVendorId());
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping("/groups")
    public ResponseEntity<?> createHostelGroup(@RequestBody HostelGroupDTO hostelGroupDTO) {
        HostelGroup hostelGroup = new HostelGroup();
        hostelGroup.setHostelGroupName(hostelGroupDTO.getHostelGroupName());
        hostelGroup.setDetailedAddress(hostelGroupDTO.getDetailedAddress());
        hostelGroup.setLatitude(hostelGroupDTO.getLatitude());
        hostelGroup.setLongitude(hostelGroupDTO.getLongitude());
        hostelGroup.setVendor(vendorService.findVendorByVendorId(hostelGroupDTO.getVendorId()));
        hostelGroupService.saveHostelGroup(hostelGroup);
        return  ResponseEntity.ok(hostelGroupDTO);
    }

    @PutMapping("/groups/{hostelGroupId}")
    public HostelGroupDTO updateHostelGroup(@PathVariable int hostelGroupId, @RequestBody HostelGroupDTO hostelGroupRequest) {
        hostelGroupRequest.setHostelGroupId(hostelGroupId);
        return hostelGroupService.findAllHostelGroupById(hostelGroupId).map(hostelGroup -> {
            hostelGroup.setHostelGroupName(hostelGroupRequest.getHostelGroupName());
            hostelGroup.setDetailedAddress(hostelGroupRequest.getDetailedAddress());
            hostelGroup.setLatitude(hostelGroupRequest.getLatitude());
            hostelGroup.setLongitude(hostelGroupRequest.getLongitude());
            hostelGroup.setVendor(vendorService.findVendorByVendorId(hostelGroupRequest.getVendorId()));
            hostelGroupService.saveHostelGroup(hostelGroup);
            return hostelGroupRequest;
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Group not found with id " + hostelGroupId));
    }

    @DeleteMapping("/groups/{hostelGroupId}")
    public ResponseEntity<?> removeHostelGroup(@PathVariable int hostelGroupId) {
        return hostelGroupService.findAllHostelGroupById(hostelGroupId).map(hostelGroup -> {
            hostelGroupService.removeHostelGroup(hostelGroup);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Group not found with id " + hostelGroupId));
    }
}
