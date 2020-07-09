package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.avengers.capstone.hostelrenting.service.ProvinceService;
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
import static org.avengers.capstone.hostelrenting.Constant.Pagination.*;

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
     * @param rqDTO
     * @return province object has been created
     */
    @PostMapping("/provinces")
    public ResponseEntity<ApiSuccess> createProvince(@Valid @RequestBody ProvinceDTO rqDTO) throws DuplicateKeyException {
        Province rqModel = modelMapper.map(rqDTO, Province.class);
        Province createdModel = provinceService.save(rqModel);
        ProvinceDTO responseDTO = modelMapper.map(createdModel, ProvinceDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, Province.class.getSimpleName())));
    }

    /**
     * Get list all of provinces
     *
     * @return List of all provinces
     */
    @GetMapping("/provinces")
    public ResponseEntity<ApiSuccess> getAllProvinces(@RequestParam(required = false) String provinceName,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                                      @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<ProvinceDTO> results = provinceService.findAll()
                .stream()
                .filter(province -> {
                    if (provinceName != null)
                        return province.getProvinceName().toLowerCase().contains(provinceName.trim().toLowerCase());
                    return true;
                }).skip(page * size)
                .limit(size)
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
     * Find Province with provinceId
     *
     * @param provinceId
     * @return province object with the request id
     * @throws EntityNotFoundException when provinceId not found
     */
    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> getProvinceById(@PathVariable Integer provinceId) throws EntityNotFoundException {
        Province existedModel = provinceService.findById(provinceId);
        ProvinceDTO resDTO = modelMapper.map(existedModel, ProvinceDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, "Province")));
    }

    /**
     * @param provinceId
     * @param rqDTO
     * @return province object has been updated
     * @throws EntityNotFoundException
     */
    @PutMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer provinceId,
                                             @RequestBody ProvinceDTO rqDTO) throws EntityNotFoundException {
        Province rqModel = modelMapper.map(rqDTO, Province.class);
        rqModel.setProvinceId(provinceId);
        ProvinceDTO resDTO = modelMapper.map(provinceService.save(rqModel), ProvinceDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Province.class.getSimpleName())));
    }

    /**
     * Delete province object with request provinceId
     *
     * @param provinceId
     * @return HttpStatus OK
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/provinces/{provinceId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer provinceId) throws EntityNotFoundException {
        provinceService.deleteById(provinceId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }

}
