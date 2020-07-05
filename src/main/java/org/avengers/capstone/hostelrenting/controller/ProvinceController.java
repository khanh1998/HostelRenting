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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

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

    /**ko
     * Create a Province
     *
     * @param provinceDTO
     * @return province object has been created
     */
    @PostMapping("/provinces")
    public ResponseEntity<ApiSuccess> createProvince(@Valid @RequestBody ProvinceDTO provinceDTO) throws DuplicateKeyException {
        Province province = modelMapper.map(provinceDTO, Province.class);
        Province createdProvince = provinceService.save(province);
        provinceDTO = modelMapper.map(createdProvince, ProvinceDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(provinceDTO, String.format(CREATE_SUCCESS, "Province")));
    }

    /**
     * Get list all of provinces
     *
     * @return List of all provinces
     */
    @GetMapping("/provinces")
    public ResponseEntity<ApiSuccess> getAllProvinces() {
        List<ProvinceDTO> results = provinceService.findAll()
                .stream()
                .map(province -> modelMapper.map(province, ProvinceDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no province"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Province")));
    }

    /**
     * Find Province with provinceId
     *
     * @param provinceId
     * @return province object with the request id
     * @throws EntityNotFoundException when provinceId not found
     */
    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> getProvinceById(@PathVariable Integer provinceId) throws EntityNotFoundException {
        Province province = provinceService.findById(provinceId);
        ProvinceDTO provinceDTO = modelMapper.map(province, ProvinceDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(provinceDTO, String.format(GET_SUCCESS, "Province")));
    }

    /**
     *
     * @param provinceId
     * @param provinceDTO
     * @return province object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer provinceId, @RequestBody ProvinceDTO provinceDTO) throws EntityNotFoundException {
        Province oldProvince = modelMapper.map(provinceDTO, Province.class);
        oldProvince.setProvinceId(provinceId);
        ProvinceDTO updatedProvince = modelMapper.map(provinceService.save(oldProvince), ProvinceDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedProvince, String.format(UPDATE_SUCCESS, "Province")));
    }

    /**
     * Delete province object with request provinceId
     *
     * @param provinceId
     * @return HttpStatus OK
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer provinceId) throws EntityNotFoundException{
        provinceService.delete(provinceId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }

}
