package capstone.tdhostel.controller;

import capstone.tdhostel.dto.VendorDTO;
import capstone.tdhostel.dto.VendorNoIdDTO;
import capstone.tdhostel.exception.ResourceNotFoundException;
import capstone.tdhostel.model.Renter;
import capstone.tdhostel.model.Vendor;
import capstone.tdhostel.service.RenterService;
import capstone.tdhostel.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @GetMapping("/vendors")
    public List<Vendor> getAllVendors() {
        return vendorService.findAllVendor();
    }

    @GetMapping("/vendors/{vendorId}")
    public Vendor getVendorById(@PathVariable int vendorId) {
        return vendorService.findVendorById(vendorId).get();
    }

    @PostMapping("/vendors")
    public ResponseEntity<?> createVendor(@RequestBody VendorDTO vendorDTO) {
        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorDTO.getVendorName());
        vendor.setPassword(vendorDTO.getPassword());
        vendor.setAvatar(vendorDTO.getAvatar());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setPhoneNumber(vendorDTO.getPhoneNumber());
        vendorService.saveVendor(vendor);
        return  ResponseEntity.ok(vendorDTO);
    }

    @PutMapping("/vendors/{vendorId}")
    public VendorNoIdDTO updateVendor(@PathVariable int vendorId, @RequestBody VendorNoIdDTO vendorRequest) {
        return vendorService.findVendorById(vendorId).map(vendor -> {
            vendor.setVendorName(vendorRequest.getVendorName());
            vendor.setPhoneNumber(vendorRequest.getPhoneNumber());
            vendor.setEmail(vendorRequest.getEmail());
            vendor.setAvatar(vendorRequest.getAvatar());
            vendor.setPassword(vendorRequest.getPassword());
            vendorService.saveVendor(vendor);
            return vendorRequest;
        }).orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id " + vendorId));
    }

    @DeleteMapping("/vendors/{vendorId}")
    public ResponseEntity<?> removeVendor(@PathVariable int vendorId) {
        return vendorService.findVendorById(vendorId).map(vendor -> {
            vendorService.removeVendor(vendor);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id " + vendorId));
    }
}
