package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.hosteltype.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.HostelRoom;
import org.avengers.capstone.hostelrenting.model.HostelType;
import org.avengers.capstone.hostelrenting.service.HostelRoomService;
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
public class HostelRoomController {

    private HostelRoomService hostelRoomService;
    private HostelTypeService hostelTypeService;
    private ModelMapper modelMapper;

    @Autowired
    public void setHostelRoomService(HostelRoomService hostelRoomService) {
        this.hostelRoomService = hostelRoomService;
    }

    @Autowired
    public void setHostelTypeService(HostelTypeService hostelTypeService) {
        this.hostelTypeService = hostelTypeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/types/{typeId}/rooms")
    public ResponseEntity<ApiSuccess> getRoomsByTypeId(@PathVariable Integer typeId) throws EntityNotFoundException {
        HostelType existedType = hostelTypeService.findById(typeId);
        List<HostelRoomDTO> rooms = existedType.getHostelRooms()
                .stream()
                .map(hostelRoom -> modelMapper.map(hostelRoom, HostelRoomDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(rooms, String.format(GET_SUCCESS, HostelType.class.getSimpleName())));
    }

    @GetMapping("/types/{typeId}/rooms/{roomId}")
    public ResponseEntity<ApiSuccess> getRoomByTypeId(@PathVariable Integer typeId,
                                                      @PathVariable Integer roomId) throws EntityNotFoundException {
        HostelType existedType = hostelTypeService.findById(typeId);
        HostelRoom resModel = existedType.getHostelRooms()
                .stream()
                .filter(r -> r.getRoomId() == roomId)
                .collect(Collectors.toList())
                .get(0);
        TypeDTOResponse resDTO = modelMapper.map(resModel, TypeDTOResponse.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, HostelRoom.class.getSimpleName())));
    }

    @PostMapping("types/{typeId}/rooms")
    public ResponseEntity<ApiSuccess> createDistrict(@PathVariable Integer typeId,
                                                     @Valid @RequestBody HostelRoomDTO rqRoom) throws DuplicateKeyException {
        HostelType existedType = hostelTypeService.findById(typeId);
        HostelRoom model = modelMapper.map(rqRoom, HostelRoom.class);
        model.setHostelType(existedType);
        HostelRoom createdModel = hostelRoomService.save(model);
        HostelRoomDTO createdDTO = modelMapper.map(model, HostelRoomDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, HostelRoom.class.getSimpleName())));
    }

    @PutMapping("/types/{typeId}/rooms/{roomId}")
    public ResponseEntity<ApiSuccess> updateDistrict(@PathVariable Integer typeId,
                                                     @PathVariable Integer roomId,
                                                     @Valid @RequestBody HostelRoomDTO rqRoom) throws EntityNotFoundException {
        // check that id exist or not
        HostelType existedType = hostelTypeService.findById(typeId);
        HostelRoom existedRoom = hostelRoomService.findById(roomId);

        rqRoom.setRoomId(existedRoom.getRoomId());
        HostelRoom rqModel = modelMapper.map(rqRoom, HostelRoom.class);
        rqModel.setHostelType(existedType);
        HostelRoom updatedModel = hostelRoomService.save(rqModel);
        HostelRoomDTO resDTO = modelMapper.map(updatedModel, HostelRoomDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, HostelRoom.class.getSimpleName())));
    }


}
