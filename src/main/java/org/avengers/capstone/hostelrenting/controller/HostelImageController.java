package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.DistrictDTO;
import org.avengers.capstone.hostelrenting.dto.HostelImageDTO;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.District;
import org.avengers.capstone.hostelrenting.model.HostelImage;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.service.DistrictService;
import org.avengers.capstone.hostelrenting.service.HostelImageService;
import org.avengers.capstone.hostelrenting.service.HostelTypeService;
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

@RestController
@RequestMapping("/api/v1")
public class HostelImageController {
    private HostelImageService hostelImageService;
    private HostelTypeService hostelTypeService;
    private ModelMapper modelMapper;

    @Autowired
    public void setHostelImageService(HostelImageService hostelImageService) {
        this.hostelImageService = hostelImageService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/images")
    public ResponseEntity<ApiSuccess> getAllImage() {
        List<HostelImage> results = hostelImageService.findAllHostelImage()
                .stream()
                .map(hostelImage -> modelMapper.map(hostelImage, HostelImage.class))
                .collect(Collectors.toList());

        if (results.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiSuccess("There is no image"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess( results, String.format(GET_SUCCESS, "Image")));
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<ApiSuccess> getHostelImageById(@PathVariable Integer imageId) throws EntityNotFoundException {
        HostelImage hostelImage = hostelImageService.findHostelImageById(imageId);
        HostelImageDTO hostelImageDTO = modelMapper.map(hostelImage, HostelImageDTO.class);
        hostelImageDTO.setHostelTypeId(hostelImage.getHostelType().getHostelTypeId());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(hostelImageDTO, String.format(GET_SUCCESS, "Image")));
    }

    @PostMapping("/images")
    public ResponseEntity<ApiSuccess> createHostelImage(@Valid @RequestBody HostelImageDTO hostelImageDTO) throws DuplicateKeyException {

        HostelImage hostelImage = modelMapper.map(hostelImageDTO, HostelImage.class);
        hostelImage.setHostelType(hostelTypeService.findHostelTypeByHostelTypeId(hostelImageDTO.getHostelTypeId()));
        HostelImage createdHostelImage = hostelImageService.saveHostelImage(hostelImage);
        hostelImageDTO = modelMapper.map(createdHostelImage, HostelImageDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiSuccess(hostelImageDTO, String.format(CREATE_SUCCESS, "Image")));
    }

    @PutMapping("/images/{imageId}")
    public ResponseEntity<ApiSuccess> updateHostelImage(@PathVariable Integer imageId, @RequestBody HostelImageDTO hostelImageDTO) throws EntityNotFoundException {
        HostelImage oldHostelImage = modelMapper.map(hostelImageDTO, HostelImage.class);
        oldHostelImage.setImageId(imageId);
        HostelImageDTO updatedHostelImage = modelMapper.map(hostelImageService.saveHostelImage(oldHostelImage), HostelImageDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess(updatedHostelImage, String.format(UPDATE_SUCCESS, "Image")));
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<ApiSuccess> deleteHostelImage(@PathVariable Integer imageId) throws EntityNotFoundException{
        hostelImageService.deleteHostelImage(imageId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiSuccess("Deleted successfully"));
    }
}
