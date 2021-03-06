package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.HostelRoomDTO;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.service.RoomService;
import org.avengers.capstone.hostelrenting.service.TypeService;
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
public class RoomController {

    private RoomService roomService;
    private TypeService typeService;
    private ModelMapper modelMapper;

    @Autowired
    public void setHostelRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @Autowired
    public void setHostelTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/types/{typeId}/rooms")
    public ResponseEntity<ApiSuccess> getRoomsByTypeId(@PathVariable Integer typeId) throws EntityNotFoundException {
        Type existedType = typeService.findById(typeId);
        List<HostelRoomDTO> rooms = existedType.getRooms()
                .stream()
                .map(hostelRoom -> modelMapper.map(hostelRoom, HostelRoomDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(rooms, String.format(GET_SUCCESS, Type.class.getSimpleName())));
    }

    @GetMapping("/types/{typeId}/rooms/{roomId}")
    public ResponseEntity<ApiSuccess> getRoomByTypeId(@PathVariable Integer typeId,
                                                      @PathVariable Integer roomId) throws EntityNotFoundException {
        Type existedType = typeService.findById(typeId);
        Room resModel = existedType.getRooms()
                .stream()
                .filter(r -> r.getRoomId() == roomId)
                .collect(Collectors.toList())
                .get(0);
        TypeDTOResponse resDTO = modelMapper.map(resModel, TypeDTOResponse.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(GET_SUCCESS, Room.class.getSimpleName())));
    }

    @PostMapping("types/{typeId}/rooms")
    public ResponseEntity<ApiSuccess> createDistrict(@PathVariable Integer typeId,
                                                     @Valid @RequestBody HostelRoomDTO rqRoom) throws DuplicateKeyException {
        Type existedType = typeService.findById(typeId);
        Room model = modelMapper.map(rqRoom, Room.class);
        model.setType(existedType);
        Room createdModel = roomService.save(model);
        HostelRoomDTO createdDTO = modelMapper.map(model, HostelRoomDTO.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiSuccess(createdDTO, String.format(CREATE_SUCCESS, Room.class.getSimpleName())));
    }

    @PutMapping("/types/{typeId}/rooms/{roomId}")
    public ResponseEntity<ApiSuccess> updateDistrict(@PathVariable Integer typeId,
                                                     @PathVariable Integer roomId,
                                                     @Valid @RequestBody HostelRoomDTO rqRoom) throws EntityNotFoundException {
        // check that id exist or not
        Type existedType = typeService.findById(typeId);
        Room existedRoom = roomService.findById(roomId);

        rqRoom.setRoomId(existedRoom.getRoomId());
        Room rqModel = modelMapper.map(rqRoom, Room.class);
        rqModel.setType(existedType);
        Room updatedModel = roomService.save(rqModel);
        HostelRoomDTO resDTO = modelMapper.map(updatedModel, HostelRoomDTO.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Room.class.getSimpleName())));
    }


}
