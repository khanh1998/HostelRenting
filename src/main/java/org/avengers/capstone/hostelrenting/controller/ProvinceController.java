package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOCreate;
import org.avengers.capstone.hostelrenting.dto.province.ProvinceDTOResponse;
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

import java.util.Collection;
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
    public ResponseEntity<?> createNewProvinces(@Validated @RequestBody Collection<ProvinceDTOCreate> reqDTOs) throws DuplicateKeyException {
        Collection<Province> reqModels = reqDTOs.stream().map(provinceDTOShort -> modelMapper.map(provinceDTOShort, Province.class)).collect(Collectors.toList());
        Collection<ProvinceDTOResponse> resDTOs = reqModels.stream().map(reqModel -> {
            Province resModel = provinceService.create(reqModel);
            return modelMapper.map(resModel, ProvinceDTOResponse.class);
        }).collect(Collectors.toList());

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Provinces has been created successfully!");

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
        List<ProvinceDTO> resDTOs = provinceService.getAll()
                .stream()
                .map(province -> {
                    if (truncated)
                        return modelMapper.map(province, ProvinceDTO.class);
                    else
                        return modelMapper.map(province, ProvinceDTOResponse.class);
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
        ProvinceDTOResponse resDTO = modelMapper.map(existedModel, ProvinceDTOResponse.class);

        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTO, resMsg);

        return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
    }


}
