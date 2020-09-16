package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOFull;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOShort;
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

import java.util.List;
import java.util.stream.Collectors;

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
     * Create a Province
     *
     * @param reqDTO request DTO
     * @return province object has been created
     */
    @PostMapping("/provinces")
    public ResponseEntity<?> create(@Validated @RequestBody ProvinceDTOFull reqDTO) throws DuplicateKeyException {
        Province reqModel = modelMapper.map(reqDTO, Province.class);
        Province resModel = provinceService.save(reqModel);
        ProvinceDTOFull resDTO = modelMapper.map(resModel, ProvinceDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, "Province has been created successfully!");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    /**
     * Get list all of provinces
     *
     * @return List of all provinces
     */
    @GetMapping("/provinces")
    public ResponseEntity<?> getAllProvinces(@RequestParam(defaultValue = "false") boolean truncated) {
        String resMsg = "Province(s) has been retrieved successfully!";
        List<ProvinceDTOShort> resDTOs = provinceService.getAll()
                .stream()
                .map(province -> {
                    if (truncated)
                        return modelMapper.map(province, ProvinceDTOShort.class);
                    else
                        return modelMapper.map(province, ProvinceDTOFull.class);
                })
                .collect(Collectors.toList());
        if (resDTOs.isEmpty())
            resMsg = "There is no province";

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }

    /**
     * Find a Province with provinceId
     *
     * @param provinceId the given id
     * @return province object with the request id
     * @throws EntityNotFoundException when provinceId not found
     */
    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<?> getById(@PathVariable Integer provinceId) throws EntityNotFoundException {
        String resMsg = "Province has been retrieved successfully!";

        Province existedModel = provinceService.findById(provinceId);
        ProvinceDTOFull resDTO = modelMapper.map(existedModel, ProvinceDTOFull.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
