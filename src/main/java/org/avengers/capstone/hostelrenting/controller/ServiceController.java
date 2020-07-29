package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ServiceDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Service;
import org.avengers.capstone.hostelrenting.service.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class ServiceController {

    private ServiceService serviceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * create a Service with a request service obj
     *
     * @param reqDTO
     * @return reqDTO
     * @throws DuplicateKeyException
     */
    @PostMapping("/services")
    public ResponseEntity<ApiSuccess> create(@RequestBody @Valid ServiceDTO reqDTO) throws DuplicateKeyException {
        Service rqModel = modelMapper.map(reqDTO, Service.class);
        Service createdModel = serviceService.save(rqModel);
        ServiceDTO responseDTO = modelMapper.map(createdModel, ServiceDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, Service.class.getSimpleName())));
    }

//    /**
//     * Filter and get list all of provinces
//     *
//     * @param serviceName
//     * @param size
//     * @param page
//     * @return
//     */
//    @GetMapping("/services")
//    public ResponseEntity<ApiSuccess> getAllProvinces(@RequestParam(required = false) String serviceName,
//                                                      @RequestParam(required = false) Integer servicePrice,
//                                                      @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
//                                                      @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
//        List<ServiceDTO> responses = serviceService.findAll()
//                .stream()
//                .filter(service -> {
//                    if (serviceName != null)
//                        return service.getServiceName().toLowerCase().contains(serviceName.trim().toLowerCase());
//                    return true;
//                }).filter(service -> {
//                    if (servicePrice != null)
//                        return service.getServicePrice() == servicePrice;
//                    return true;
//                })
//                .skip((page-1) * size)
//                .limit(size)
//                .map(service -> modelMapper.map(service, ServiceDTO.class))
//                .collect(Collectors.toList());
//
//        if (responses.isEmpty()) {
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(new ApiSuccess("There is no services"));
//        }
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(new ApiSuccess(responses, String.format(GET_SUCCESS, Service.class.getSimpleName())));
//    }

    /**
     * Find a Service with serviceId
     *
     * @param serviceId
     * @return a Service object with the request id
     * @throws EntityNotFoundException
     */
    @GetMapping("/services/{serviceId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer serviceId) throws EntityNotFoundException {
        Service existedModel = serviceService.findById(serviceId);
        ServiceDTO resDTO = modelMapper.map(existedModel, ServiceDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Service.class.getSimpleName())));
    }

    /**
     *  Update a service obj with reqDTO and id
     *
     * @param serviceId
     * @param reqDTO
     * @return service object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/services/{serviceId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer serviceId,
                                             @RequestBody @Valid ServiceDTO reqDTO) throws EntityNotFoundException {
        Service rqModel = modelMapper.map(reqDTO, Service.class);
        rqModel.setServiceId(serviceId);
        ServiceDTO resDTO = modelMapper.map(serviceService.save(rqModel), ServiceDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Service.class.getSimpleName())));
    }

    /**
     * Delete a service object by id
     *
     * @param serviceId
     * @return ResponseEntity wrap ApiSuccess
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/services/{serviceId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer serviceId) throws EntityNotFoundException {
        serviceService.deleteById(serviceId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }
}
