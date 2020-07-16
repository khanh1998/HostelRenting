package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.ProvinceDTO;
import org.avengers.capstone.hostelrenting.dto.TypeStatusDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Province;
import org.avengers.capstone.hostelrenting.model.TypeStatus;
import org.avengers.capstone.hostelrenting.service.TypeStatusService;
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
public class TypeStatusController {
    private TypeStatusService typeStatusService;
    private ModelMapper modelMapper;

    @Autowired
    public void setTypeStatusService(TypeStatusService typeStatusService) {
        this.typeStatusService = typeStatusService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("/typestatuses")
    public ResponseEntity<ApiSuccess> createTypeStatus(@Valid @RequestBody TypeStatusDTO rqDTO) throws DuplicateKeyException {
        TypeStatus rqModel = modelMapper.map(rqDTO, TypeStatus.class);
        TypeStatus createdModel = typeStatusService.save(rqModel);
        TypeStatusDTO responseDTO = modelMapper.map(createdModel, TypeStatusDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(responseDTO, String.format(CREATE_SUCCESS, TypeStatus.class.getSimpleName())));
    }

    @GetMapping("/typestatuses")
    public ResponseEntity<ApiSuccess> getAll(@RequestParam (required = false) String typeStatusName,
                                             @RequestParam(required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                             @RequestParam(required = false, defaultValue = DEFAULT_PAGE) Integer page) {
        List<TypeStatusDTO> results = typeStatusService.findAll()
                .stream()
                .filter(typeStatus -> {
                    if (typeStatusName != null)
                        return typeStatus.getStatusName().toLowerCase().contains(typeStatusName.trim().toLowerCase());
                    return true;
                }).skip((page-1) * size)
                .limit(size)
                .map(typeStatus -> modelMapper.map(typeStatus, TypeStatusDTO.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(new ApiSuccess("There is no typeStatus"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess( results, String.format(GET_SUCCESS, TypeStatus.class.getSimpleName())));
    }

    @GetMapping("/typestatuses/{typeStatusId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer typeStatusId) throws EntityNotFoundException {
        TypeStatus existedModel = typeStatusService.findById(typeStatusId);
        TypeStatusDTO resDTO = modelMapper.map(existedModel, TypeStatusDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, TypeStatus.class.getSimpleName())));
    }

    @PutMapping("/typestatuses/{typeStatusId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer typeStatusId,
                                             @RequestBody TypeStatusDTO rqDTO) throws EntityNotFoundException {
        TypeStatus rqModel = modelMapper.map(rqDTO, TypeStatus.class);
        rqModel.setStatusId(typeStatusId);
        TypeStatusDTO resDTO = modelMapper.map(typeStatusService.save(rqModel), TypeStatusDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, TypeStatus.class.getSimpleName())));
    }

    @DeleteMapping("/typestatuses/{typeStatusId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer typeStatusId) throws EntityNotFoundException {
        typeStatusService.deleteById(typeStatusId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
