package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOFull;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOFull;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.RenterDTO;
import org.avengers.capstone.hostelrenting.dto.vendor.VendorDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.DealService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.avengers.capstone.hostelrenting.service.RenterService;
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

@RestController
@RequestMapping("/api/v1")
public class DealController {

    private ModelMapper modelMapper;
    private RenterService renterService;
    private VendorService vendorService;
    private HostelTypeService hostelTypeService;
    private DealService dealService;

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @PostMapping("/deals")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid DealDTOShort reqDTO) {
        // check that vendor, renter and room is existed or not
        Vendor existedVendor = vendorService.findById(reqDTO.getVendorId());
        Renter existedRenter = renterService.findById(reqDTO.getRenterId());
        HostelType existedType = hostelTypeService.findById(reqDTO.getTypeId());

        Deal reqModel = modelMapper.map(reqDTO, Deal.class);
        reqModel.setVendor(existedVendor);
        reqModel.setRenter(existedRenter);
        reqModel.setHostelType(existedType);

        Deal resModel  = dealService.save(reqModel);
        DealDTOShort resDTO = modelMapper.map(resModel, DealDTOShort.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Deal.class.getSimpleName())));
    }

    @GetMapping("/renters/{renterId}/deals")
    public ResponseEntity<ApiSuccess> getByRenterId(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedRenter = renterService.findById(renterId);
        List<DealDTOFull> resDeals = existedRenter.getDeals()
                .stream()
                .map(deal -> modelMapper.map(deal, DealDTOFull.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDeals, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/deals/{dealId}")
    public ResponseEntity<ApiSuccess> getBookingById(@PathVariable Integer dealId) throws EntityNotFoundException{
        Deal existedModel = dealService.findById(dealId);
        DealDTOFull resDTO = modelMapper.map(existedModel, DealDTOFull.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Deal.class.getSimpleName())));
    }

    @GetMapping("/vendors/{vendorId}/deals")
    public ResponseEntity<ApiSuccess> getByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedVendor = vendorService.findById(vendorId);
        List<DealDTOFull> resDeals = existedVendor.getDeals()
                .stream()
                .map(deal -> modelMapper.map(deal, DealDTOFull.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDeals, String.format(GET_SUCCESS, Vendor.class.getSimpleName())));
    }
}
