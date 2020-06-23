package capstone.tdhostel.controller;

import capstone.tdhostel.dto.*;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.Booking;
import capstone.tdhostel.model.HostelGroup;
import capstone.tdhostel.model.HostelType;
import capstone.tdhostel.model.Vendor;
import capstone.tdhostel.service.BookingService;
import capstone.tdhostel.service.HostelGroupService;
import capstone.tdhostel.service.HostelTypeService;
import capstone.tdhostel.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HostelTypeController {
    @Autowired
    private HostelTypeService hostelTypeService;

    @Autowired
    private HostelGroupService hostelGroupService;

    @Autowired
    private VendorService vendorService;

    @GetMapping("/types")
    public List<HostelType> getAllHostelTypes() {
        return hostelTypeService.findAllHostelType();
    }

    @GetMapping("/types/{hostelTypeId}")
    public HostelTypeListDTO getHostelTypeById(@PathVariable int hostelTypeId) {
        return hostelTypeService.findHostelTypeById(hostelTypeId).map(hostelType -> {
            HostelTypeListDTO dto = new HostelTypeListDTO();
            dto.setHostelTypeId(hostelTypeId);
            dto.setHostelName(hostelType.getHostelTypeName());
            dto.setPrice(hostelType.getPrice());
            dto.setUnit(hostelType.getUnit());
            dto.setCapacity(hostelType.getCapacity());
            dto.setSuperficiality(hostelType.getSuperficiality());
            dto.setHostelGroupId(hostelType.getHostelGroup().getHostelGroupId());
            dto.setBookingId(hostelType.getBookings().stream().map(booking -> booking.getBookingId()).collect(Collectors.toList()));
//            hostelType.setHostelGroup(hostelGroupService.getAllHostelGroup());
            return dto;
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Type not found with id "+ hostelTypeId));
    }

    @GetMapping("/types/group/{hostelGroupId}")
    public List<HostelTypeListDTO> getHostelTypeByHostelGroupId(@PathVariable int hostelGroupId) {
        return hostelTypeService.findAllHostelTypeByHostelGroupId(hostelGroupId).stream().map(hostelType -> {
            HostelTypeListDTO dto = new HostelTypeListDTO();
            dto.setHostelTypeId(hostelType.getHostelTypeId());
            dto.setHostelName(hostelType.getHostelTypeName());
            dto.setPrice(hostelType.getPrice());
            dto.setUnit(hostelType.getUnit());
            dto.setCapacity(hostelType.getCapacity());
            dto.setSuperficiality(hostelType.getSuperficiality());
            dto.setBookingId(hostelType.getBookings().stream().map(booking -> booking.getBookingId()).collect(Collectors.toList()));
            dto.setHostelGroupId(hostelType.getHostelGroup().getHostelGroupId());
            return dto;
        }).collect(Collectors.toList());
    }

    @PostMapping("/types")
    public  ResponseEntity<?> createHostelType(@RequestBody HostelTypeDTO hostelTypeDTO) {
        HostelType hostelType = new HostelType();
        hostelType.setHostelTypeName(hostelTypeDTO.getHostelName());
        hostelType.setPrice(hostelTypeDTO.getPrice());
        hostelType.setUnit(hostelTypeDTO.getUnit());
        hostelType.setCapacity(hostelTypeDTO.getCapacity());
        hostelType.setSuperficiality(hostelTypeDTO.getSuperficiality());
        hostelType.setHostelGroup(hostelGroupService.findAllHostelGroupById(hostelTypeDTO.getHostelGroupId()).get());
        hostelTypeService.saveHostelType(hostelType);
        return  ResponseEntity.ok(hostelTypeDTO);
    }

    @PutMapping("/types/{hostelTypeId}")
    public HostelTypeNoIdDTO updateHostelType(@PathVariable int hostelTypeId, @RequestBody HostelTypeNoIdDTO hostelTypeRequest) {
        return hostelTypeService.findHostelTypeById(hostelTypeId).map(hostelType -> {
            hostelType.setHostelTypeName(hostelTypeRequest.getHostelName());
            hostelType.setPrice(hostelTypeRequest.getPrice());
            hostelType.setUnit(hostelTypeRequest.getUnit());
            hostelType.setSuperficiality(hostelTypeRequest.getSuperficiality());
            hostelType.setCapacity(hostelTypeRequest.getCapacity());
            hostelTypeService.saveHostelType(hostelType);
            return hostelTypeRequest;
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Type not found with id " + hostelTypeId));
    }

    @DeleteMapping("/types/{hostelTypeId}")
    public ResponseEntity<?> removeHostelType(@PathVariable int hostelTypeId) {
        return hostelTypeService.findHostelTypeById(hostelTypeId).map(hostelType -> {
            hostelTypeService.removeHostelType(hostelType);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Type not found with id " + hostelTypeId));
    }

    @GetMapping("/types/view/{hostelTypeId}")
    public ViewDetailDTO viewDetailByHostelTypeId(@PathVariable int hostelTypeId) {
        return hostelTypeService.findHostelTypeById(hostelTypeId).map(hostelType -> {
            ViewDetailDTO dto = new ViewDetailDTO();
            dto.setHostelGroupId(hostelType.getHostelGroup().getHostelGroupId());
            dto.setHostelTypeName(hostelType.getHostelTypeName());
            dto.setPrice(hostelType.getPrice());
            dto.setSuperficiality(hostelType.getSuperficiality());
            dto.setCapacity(hostelType.getCapacity());
            dto.setHostelGroupName(hostelType.getHostelGroup().getHostelGroupName());
            dto.setDetailedAddress(hostelGroupService.findHostelGroupById(hostelType.getHostelGroup().getHostelGroupId()).getDetailedAddress());

            dto.setVendorName(vendorService.findVendorByVendorId(hostelGroupService.findHostelGroupById(hostelType.getHostelGroup().getHostelGroupId()).getVendor().getVendorId()).getVendorName());
            dto.setAvatar(vendorService.findVendorByVendorId(hostelGroupService.findHostelGroupById(hostelType.getHostelGroup().getHostelGroupId()).getVendor().getVendorId()).getVendorName());
            return dto;
        }).orElseThrow(() -> new ResourceNotFoundException("Hostel Type not found with id "+ hostelTypeId));
    }
}
