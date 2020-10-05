package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.hostelgroup.GroupDTOResponse;
import org.avengers.capstone.hostelrenting.dto.booking.BookingDTOShort;
import org.avengers.capstone.hostelrenting.dto.deal.DealDTOShort;
import org.avengers.capstone.hostelrenting.dto.hosteltype.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOFull;
import org.avengers.capstone.hostelrenting.dto.contract.ContractDTOShort;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ContractController {
    private ModelMapper modelMapper;
    private ContractService contractService;
    private VendorService vendorService;
    private RenterService renterService;
    private HostelRoomService roomService;
    private HostelTypeService typeService;
    private HostelGroupService groupService;
    private DealService dealService;
    private BookingService bookingService;

    @Autowired
    public void setDealService(DealService dealService) {
        this.dealService = dealService;
    }

    @Autowired
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.typeService = hostelTypeService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.groupService = hostelGroupService;
    }

    @Autowired
    public void setVendorService(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setHostelRoomService(HostelRoomService hostelRoomService) {
        this.roomService = hostelRoomService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setContractService(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/contracts")
    public ResponseEntity<?> create(@RequestBody @Valid ContractDTOShort reqDTO) {
        Contract resModel = contractService.create(reqDTO);
        ContractDTOFull resDTO = modelMapper.map(resModel, ContractDTOFull.class);

        // get deal, booking, group and type
        getFullAttributesForDTO(resDTO);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your contract has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/contracts")
    public ResponseEntity<?> getByRenterId(@PathVariable Long renterId) throws EntityNotFoundException {
        String resMsg = "Your contract(s) has been retrieved successfully!";

        List<ContractDTOFull> resDTOs = contractService.findByRenterId(renterId)
                .stream()
                .map(contract -> modelMapper.map(contract, ContractDTOFull.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no contract";

        // get deal, booking, group and type
        resDTOs.forEach(contractDTOFull -> getFullAttributesForDTO(contractDTOFull));

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/contracts")
    public ResponseEntity<?> getByVendorId(@PathVariable Long vendorId) throws EntityNotFoundException {
        String resMsg = "Your contract(s) has been retrieved successfully!";

        List<ContractDTOFull> resDTOs = contractService.findByVendorId(vendorId)
                .stream()
                .map(contract -> modelMapper.map(contract, ContractDTOFull.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no contract";

        // get deal, booking, group and type
        resDTOs.forEach(contractDTOFull -> getFullAttributesForDTO(contractDTOFull));

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    private void getFullAttributesForDTO(ContractDTOFull resDTO){
        //get deal
        if (resDTO.getDeal() != null)
            getDealForDTO(resDTO);

        //get booking
        if (resDTO.getBooking() != null)
            getBookingForDTO(resDTO);

        //get type & group
        getTypeAndGroupForDTO(resDTO);

        Renter renter = contractService.findById(resDTO.getContractId()).getRenter();
    }

    private void getDealForDTO(ContractDTOFull resDTO) {
        Deal exDeal = dealService.findById(resDTO.getDeal().getDealId());
        resDTO.setDeal(modelMapper.map(exDeal, DealDTOShort.class));
    }

    private void getBookingForDTO(ContractDTOFull resDTO) {
        Booking exBooking = bookingService.findById(resDTO.getBooking().getBookingId());
        resDTO.setBooking(modelMapper.map(exBooking, BookingDTOShort.class));
    }

    private void getTypeAndGroupForDTO(ContractDTOFull resDTO) {
        HostelType exType = typeService.findById(resDTO.getRoom().getTypeId());
        resDTO.setType(modelMapper.map(exType, TypeDTOResponse.class));
        resDTO.setGroup(modelMapper.map(groupService.findById(exType.getHostelGroup().getGroupId()), GroupDTOResponse.class));
    }
}
