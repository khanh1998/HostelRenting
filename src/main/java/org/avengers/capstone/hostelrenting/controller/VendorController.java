package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.VendorDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.Vendor;
import org.avengers.capstone.hostelrenting.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class VendorController {

    private ModelMapper modelMapper;
    private VendorService vendorService;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/vendors")
    public ResponseEntity<ApiSuccess> create(@Valid @RequestBody VendorDTO reqDTO) throws EntityNotFoundException {
        Vendor rqModel = modelMapper.map(reqDTO, Vendor.class);
        Vendor createdModel = vendorService.save(rqModel);

        VendorDTO resDTO = modelMapper.map(createdModel, VendorDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Vendor.class.getSimpleName())));
    }

    @GetMapping("/vendors")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam(required = false) String username,
                                             @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<VendorDTO> results = vendorService.findAll()
                .stream()
                .filter(vendor -> {
                    if (username != null)
                        return vendor.getUsername().toLowerCase().contains(username.trim().toLowerCase());
                    return true;
                }).skip(page * size)
                .limit(size)
                .map(vendor -> modelMapper.map(vendor, VendorDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no vendor"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(results, String.format(GET_SUCCESS, Vendor.class.getSimpleName())));
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);
        VendorDTO resDTO = modelMapper.map(existedModel, VendorDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Vendor.class.getSimpleName())));
    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer vendorId,
                                             @RequestBody VendorDTO reqDTO) throws EntityNotFoundException {
        Vendor existedModel = vendorService.findById(vendorId);

        Vendor rqModel = modelMapper.map(reqDTO, Vendor.class);
        rqModel.setVendorId(vendorId);
        rqModel.setUsername(existedModel.getUsername());
        VendorDTO resDTO = modelMapper.map(vendorService.save(rqModel), VendorDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Vendor.class.getSimpleName())));
    }

    @DeleteMapping("/vendors/{vendorId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer vendorId) throws EntityNotFoundException {
        vendorService.deleteById(vendorId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }
}
