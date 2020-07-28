package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_PAGE;
import static org.avengers.capstone.hostelrenting.Constant.Pagination.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/v1")
public class ProvinceController {
    private ProvinceService provinceService;
    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setProvinceService(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    /**
     * Get list all of provinces
     *
     * @return List of all provinces
     */
    @GetMapping("/provinces")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam(required = false, name = "name") String provinceName,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<ProvinceDTO> results = provinceService.findAll(page, size)
                .stream()
                .filter(province -> {
                    if (provinceName != null)
                        return province.getProvinceName().toLowerCase().contains(provinceName.trim().toLowerCase());
                    return true;
                })
                .map(province -> modelMapper.map(province, ProvinceDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no province"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(results, String.format(GET_SUCCESS, Province.class.getSimpleName())));
    }

    /**
     * Find a Province with provinceId
     *
     * @param provinceId
     * @return province object with the request id
     * @throws EntityNotFoundException when provinceId not found
     */
    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer provinceId) throws EntityNotFoundException {
        Province existedModel = provinceService.findById(provinceId);
        ProvinceDTO resDTO = modelMapper.map(existedModel, ProvinceDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Province.class.getSimpleName())));
    }

    /**
     * Create a Province
     *
     * @param reqDTO
     * @return province object has been created
     */
    @PostMapping("/provinces")
    public ResponseEntity<ApiSuccess> create(@Validated @RequestBody ProvinceDTO reqDTO) throws DuplicateKeyException, ConstraintViolationException {
        Province rqModel = modelMapper.map(reqDTO, Province.class);
        Province createdModel = provinceService.save(rqModel);
        ProvinceDTO responseDTO = modelMapper.map(createdModel, ProvinceDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, Province.class.getSimpleName())));
    }

    /**
     * Update a province obj with reqDTO and id
     *
     * @param provinceId
     * @param reqDTO
     * @return province object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer provinceId,
                                             @RequestBody ProvinceDTO reqDTO) throws EntityNotFoundException {
        Province rqModel = modelMapper.map(reqDTO, Province.class);
        rqModel.setProvinceId(provinceId);
        ProvinceDTO resDTO = modelMapper.map(provinceService.save(rqModel), ProvinceDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Province.class.getSimpleName())));
    }

}
