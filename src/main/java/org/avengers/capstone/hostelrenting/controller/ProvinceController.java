package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.Constant;
import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
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
@RequestMapping("/api/v1/provinces")
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

    /**ko
     * Create a Province
     *
     * @param provinceDTO
     * @return province object has been created
     */
    @PostMapping
    public ApiSuccess<ProvinceDTO> createProvince(@Valid @RequestBody ProvinceDTO provinceDTO) {
        Province province = modelMapper.map(provinceDTO, Province.class);
        Province createdProvince = provinceService.save(province);
        provinceDTO = modelMapper.map(createdProvince, ProvinceDTO.class);

        return new ApiSuccess(provinceDTO, String.format(CREATE_SUCCESS, "Province"));
    }

    /**
     * Get list all of provinces
     *
     * @return List of all provinces
     */
    @GetMapping
    public ApiSuccess<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> results = provinceService.findAll()
                .stream()
                .map(province -> modelMapper.map(province, ProvinceDTO.class))
                .collect(Collectors.toList());

        return new ApiSuccess(results, String.format(GET_SUCCESS, "Province"));
    }

    /**
     * Find Province with provinceId
     *
     * @param provinceId
     * @return province object with the request id
     * @throws EntityNotFoundException when provinceId not found
     */
    @GetMapping("/{provinceId}")
    public ApiSuccess<ProvinceDTO> getProvinceById(@PathVariable int provinceId) throws EntityNotFoundException {
        Province province = provinceService.findById(provinceId);
        ProvinceDTO provinceDTO = modelMapper.map(province, ProvinceDTO.class);

        return new ApiSuccess(provinceDTO, String.format(GET_SUCCESS, "Province"));
    }

    /**
     *
     * @param provinceId
     * @param provinceDTO
     * @return province object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/{provinceId}")
    public ApiSuccess<ProvinceDTO> update(@PathVariable int provinceId, @RequestBody ProvinceDTO provinceDTO) throws EntityNotFoundException {
        Province oldProvince = provinceService.findById(provinceId);
        oldProvince = modelMapper.map(provinceDTO, Province.class);
        oldProvince.setProvinceId(provinceId);
        ProvinceDTO updatedProvince = modelMapper.map(provinceService.save(oldProvince), ProvinceDTO.class);

        return new ApiSuccess<>(updatedProvince, String.format(UPDATE_SUCCESS, "Province"));
    }

    /**
     * Delete province object with request provinceId
     *
     * @param provinceId
     * @return HttpStatus OK
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{provinceId}")
    public ResponseEntity delete(@PathVariable int provinceId) throws EntityNotFoundException{
        Province oldProvince = provinceService.findById(provinceId);
        oldProvince.setDeleted(true);
        provinceService.save(oldProvince);

        return ResponseEntity.ok().build();
    }
}
