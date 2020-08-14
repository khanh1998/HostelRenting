package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.hosteltype.ResTypeDTO;
import org.avengers.capstone.hostelrenting.dto.TypeImageDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.model.TypeImage;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
import org.avengers.capstone.hostelrenting.service.TypeImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.avengers.capstone.hostelrenting.Constant.Message.*;

@RestController
@RequestMapping("/api/v1")
public class TypeImageController {
    private ModelMapper modelMapper;
    private TypeImageService typeImageService;
    private HostelTypeService hostelTypeService;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setTypeImageService(TypeImageService typeImageService) {
        this.typeImageService = typeImageService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @PostMapping("types/{typeId}/images")
    public ResponseEntity<ApiSuccess> create(@PathVariable Integer typeId,
                                             @Valid @RequestBody TypeImageDTO reqDTO) throws DuplicateKeyException {
        HostelType typeModel = hostelTypeService.findById(typeId);
        TypeImage reqModel = modelMapper.map(reqDTO, TypeImage.class);
        reqModel.setHostelType(typeModel);
        TypeImage resModel = typeImageService.save(reqModel);
        TypeImageDTO resDTO = modelMapper.map(resModel, TypeImageDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(resDTO, String.format(CREATE_SUCCESS, TypeImage.class.getSimpleName())));
    }

    @GetMapping("types/{typeId}/images")
    public ResponseEntity<ApiSuccess> getAll(@PathVariable Integer typeId) throws EntityNotFoundException {
        HostelType typeModel = hostelTypeService.findById(typeId);
        typeModel.getTypeImages();
        ResTypeDTO resDTO = modelMapper.map(typeModel, ResTypeDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, TypeImage.class.getSimpleName())));
    }

    @GetMapping("hosteltypes/{typeId]/images/{typeImageId}")
    public ResponseEntity<ApiSuccess> getById(@PathVariable Integer typeId,
                                              @PathVariable Integer typeImageId) throws EntityNotFoundException {
        HostelType typeModel = hostelTypeService.findById(typeId);
        TypeImage resModel = typeModel.getTypeImages()
                .stream()
                .filter(i -> i.getImageId() == typeImageId)
                .collect(Collectors.toList())
                .get(0);
        TypeImageDTO resDTO = modelMapper.map(resModel, TypeImageDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, TypeImage.class.getSimpleName())));
    }

    @PutMapping("types/{typeId}/images/{typeImageId}")
    public ResponseEntity<ApiSuccess> update(@PathVariable Integer typeId,
                                             @PathVariable Integer typeImageId,
                                             @Valid @RequestBody TypeImageDTO reqDTO) throws EntityNotFoundException {
        // check that id exist or not
        HostelType existedTypeModel = hostelTypeService.findById(typeId);
        TypeImage existedImageModel = typeImageService.findById(typeImageId);

        reqDTO.setImageId(existedImageModel.getImageId());
        TypeImage rqModel = modelMapper.map(reqDTO, TypeImage.class);
        rqModel.setHostelType(existedTypeModel);
        TypeImage updatedModel = typeImageService.save(rqModel);
        TypeImageDTO updatedDTO = modelMapper.map(updatedModel, TypeImageDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(updatedDTO, String.format(UPDATE_SUCCESS, TypeImage.class.getSimpleName())));
    }

    @DeleteMapping("types/{typeId}/images/{typeImageId}")
    public ResponseEntity<ApiSuccess> delete(@PathVariable Integer typeId,
                                             @PathVariable Integer typeImageId) throws EntityNotFoundException {
        TypeImage existedModel = typeImageService.findByIdAndHostelTypeId(typeImageId, typeId);
        typeImageService.deleteById(typeImageId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess("Deleted successfully"));
    }
}
