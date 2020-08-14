package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelGroupDTO;
import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
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

import static org.avengers.capstone.hostelrenting.Constant.Message.CREATE_SUCCESS;
import static org.avengers.capstone.hostelrenting.Constant.Message.GET_SUCCESS;

@RestController
@RequestMapping("/api/v1")
public class ContractController {
    private ModelMapper modelMapper;
    private ContractService contractService;
    private VendorService vendorService;
    private RenterService renterService;
    private HostelRoomService hostelRoomService;
    private HostelTypeService hostelTypeService;
    private HostelGroupService hostelGroupService;

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setHostelGroupService(HostelGroupService hostelGroupService) {
        this.hostelGroupService = hostelGroupService;
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
        this.hostelRoomService = hostelRoomService;
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
    public ResponseEntity<ApiSuccess> create (@RequestBody @Valid ContractDTOShort reqDTO){
        // check that vendor, renter, and room id is exist or not
        Vendor existedVendor = vendorService.findById(reqDTO.getVendorId());
        Renter existedRenter = renterService.findById(reqDTO.getRenterId());
        HostelRoom existedRoom = hostelRoomService.findById(reqDTO.getRoomId());

        Contract reqModel = modelMapper.map(reqDTO, Contract.class);
        reqModel.setHostelRoom(existedRoom);
        reqModel.setVendor(existedVendor);
        reqModel.setRenter(existedRenter);

        Contract resModel = contractService.save(reqModel);
        ContractDTOFull resDTO = modelMapper.map(resModel, ContractDTOFull.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, Contract.class.getSimpleName())));
    }

    @GetMapping("/renters/{renterId}/contracts")
    public ResponseEntity<ApiSuccess> getByRenterId(@PathVariable Integer renterId) throws EntityNotFoundException {
        Renter existedRenter = renterService.findById(renterId);
        List<ContractDTOFull> resContracts = existedRenter.getContracts()
                .stream()
                .map(contract -> modelMapper.map(contract, ContractDTOFull.class))
                .collect(Collectors.toList());

        resContracts.stream().forEach(resDTO ->{
            HostelType existedType = hostelTypeService.findById(resDTO.getRoom().getTypeId());
            HostelGroup existedGroup = hostelGroupService.findById(existedType.getHostelGroup().getGroupId());
            resDTO.setType(modelMapper.map(existedType, ResTypeDTO.class));
            resDTO.setGroup(modelMapper.map(existedGroup, HostelGroupDTO.class));
        });

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resContracts, String.format(GET_SUCCESS, Renter.class.getSimpleName())));
    }

    @GetMapping("/vendors/{vendorId}/contracts")
    public ResponseEntity<ApiSuccess> getByVendorId(@PathVariable Integer vendorId) throws EntityNotFoundException {
        Vendor existedVendor = vendorService.findById(vendorId);
        List<ContractDTOFull> resContracts = existedVendor.getContracts()
                .stream()
                .map(contract -> modelMapper.map(contract, ContractDTOFull.class))
                .collect(Collectors.toList());

        resContracts.stream().forEach(resDTO ->{
            HostelType existedType = hostelTypeService.findById(resDTO.getRoom().getTypeId());
            HostelGroup existedGroup = hostelGroupService.findById(existedType.getHostelGroup().getGroupId());
            resDTO.setType(modelMapper.map(existedType, ResTypeDTO.class));
            resDTO.setGroup(modelMapper.map(existedGroup, HostelGroupDTO.class));
        });

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resContracts, String.format(GET_SUCCESS, Vendor.class.getSimpleName())));
    }
}
