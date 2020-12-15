package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.contract.*;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.*;
import org.avengers.capstone.hostelrenting.service.GroupService;
import org.avengers.capstone.hostelrenting.service.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@Validated
@RestController
@RequestMapping("/api/v1")
public class ContractController {

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    private ModelMapper modelMapper;
    private ContractService contractService;
    private VendorService vendorService;
    private RenterService renterService;
    private RoomService roomService;
    private TypeService typeService;
    private GroupService groupService;
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
    public void setHostelTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setHostelGroupService(GroupService groupService) {
        this.groupService = groupService;
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
    public void setHostelRoomService(RoomService roomService) {
        this.roomService = roomService;
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
    public ResponseEntity<?> createContract(@RequestBody @Valid ContractDTOCreate reqDTO) {
        logger.info("START - creating contract");
        /* Prepare object for contract model */
        Vendor exVendor = vendorService.findById(reqDTO.getVendorId());
        Renter exRenter = renterService.findById(reqDTO.getRenterId());
        Room exRoom = roomService.findById(reqDTO.getRoomId());
        Booking exBooking = null;
        Deal exDeal = null;
        if (reqDTO.getBookingId()!= null){
            exBooking = bookingService.findById(reqDTO.getBookingId());
        }
        if (reqDTO.getDealId() != null){
            exDeal = dealService.findById(reqDTO.getDealId());
        }
        Contract reqModel = modelMapper.map(reqDTO, Contract.class);
        reqModel = reqModel.toBuilder().vendor(exVendor).renter(exRenter)
                .room(exRoom).deal(exDeal)
                .booking(exBooking).build();

        Contract resModel = contractService.create(reqModel);

        ContractDTOResponse resDTO = modelMapper.map(resModel, ContractDTOResponse.class);

        String msg = String.format("Your contract has been created with status: %s", resModel.getStatus());
        logger.info(msg);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, msg);
        logger.info("END - creating contract");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/contracts/confirm/{contractId}")
    public ResponseEntity<?> confirmContractById(@PathVariable Integer contractId,
                                                 @RequestBody @Valid ContractDTOConfirm reqDTO) {
        Contract exModel = contractService.findById(contractId);
        Contract resModel = contractService.confirm(exModel, reqDTO);
        ContractDTOResponse resDTO = modelMapper.map(resModel, ContractDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, String.format("Your contract has been updated with status: %s", resModel.getStatus()));

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @PutMapping("/contracts/{contractId}")
    public ResponseEntity<?> updateInactiveContract(@PathVariable Integer contractId,
                                                    @RequestBody @Valid ContractDTOUpdate reqDTO) {
        Contract exModel = contractService.findById(contractId);
        Contract resModel = contractService.updateInactiveContract(exModel, reqDTO);
        ContractDTOResponse resDTO = modelMapper.map(resModel, ContractDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, String.format("Your contract has been updated with status: %s", resModel.getStatus()));

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/renters/{renterId}/contracts")
    public ResponseEntity<?> getContractsByRenterId(@PathVariable UUID renterId,
                                                    @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                    @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                                    @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) throws EntityNotFoundException {
        String resMsg = "Your contract(s) has been retrieved successfully!";

        List<ContractDTOResponse> resDTOs = contractService.findByRenterId(renterId, page, size, sortBy, asc)
                .stream()
                .map(resDTO -> modelMapper.map(resDTO, ContractDTOResponse.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no contract";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);
        apiSuccess.setPage(page);
        apiSuccess.setSize(size);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/vendors/{vendorId}/contracts")
    public ResponseEntity<?> getContractsByVendorId(@PathVariable UUID vendorId,
                                                    @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
                                                    @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                                    @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                    @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) throws EntityNotFoundException {
        String resMsg = "Your contract(s) has been retrieved successfully!";

        List<ContractDTOResponse> resDTOs = contractService.findByVendorId(vendorId, page, size, sortBy, asc)
                .stream()
                .map(resDTO -> modelMapper.map(resDTO, ContractDTOResponse.class))
                .collect(Collectors.toList());

        if (resDTOs.isEmpty())
            resMsg = "There is no contract";

        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);
        apiSuccess.setPage(page);
        apiSuccess.setSize(size);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<?> getContractById(@PathVariable Integer contractId) {
        Contract model = contractService.findById(contractId);
        ContractDTOResponseFull resDTO = modelMapper.map(model, ContractDTOResponseFull.class);
        // Response entity
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your contract has been retrieved successfully");

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
