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
import java.util.Collection;
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


}
