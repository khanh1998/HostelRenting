package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.hostelRequest.HostelRequestDTOCreate;
import org.avengers.capstone.hostelrenting.dto.hostelRequest.HostelRequestDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.model.HostelRequest;
import org.avengers.capstone.hostelrenting.model.Renter;
import org.avengers.capstone.hostelrenting.service.HostelRequestService;
import org.avengers.capstone.hostelrenting.service.RenterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author duattt on 11/16/20
 * @created 16/11/2020 - 17:20
 * @project youthhostelapp
 */
@RestController
@RequestMapping("/api/v1")
public class HostelRequestController {
    private HostelRequestService hostelRequestService;
    private RenterService renterService;
    private ModelMapper modelMapper;

    @Autowired
    public void setRenterService(RenterService renterService) {
        this.renterService = renterService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setHostelRequestService(HostelRequestService hostelRequestService) {
        this.hostelRequestService = hostelRequestService;
    }

    @PostMapping("renters/{renterId}/requests")
    public ResponseEntity<?> createHostelRequest(@RequestBody @Valid HostelRequestDTOCreate reqDTO,
                                                 @PathVariable Long renterId) {
        // prepare ref object
        Renter exRenter =renterService.findById(renterId);

        HostelRequest reqModel = modelMapper.map(reqDTO, HostelRequest.class);
        reqModel.setRenter(exRenter);

        HostelRequest resModel = hostelRequestService.createNew(reqModel);
        HostelRequestDTOResponse resDTO = modelMapper.map(resModel, HostelRequestDTOResponse.class);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Your request has been submitted successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @GetMapping("renters/{renterId}/requests")
    public ResponseEntity<?> getRequestsByRenterId(@PathVariable Long renterId) {
        // prepare ref object

        Collection<HostelRequest> resModels = hostelRequestService.findByRenterId(renterId);
        Collection<HostelRequestDTOResponse> resDTOs = resModels
                .stream().map(request -> modelMapper.map(request, HostelRequestDTOResponse.class))
                .collect(Collectors.toList());
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Your request has been retrieved successfully!");
        apiSuccess.setTotal(resDTOs.size());

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }
}
