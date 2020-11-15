package org.avengers.capstone.hostelrenting.controller;

import org.avengers.capstone.hostelrenting.dto.room.RoomDTOCreate;
import org.avengers.capstone.hostelrenting.dto.room.RoomDTOResponse;
import org.avengers.capstone.hostelrenting.dto.type.TypeDTOResponse;
import org.avengers.capstone.hostelrenting.dto.response.ApiSuccess;
import org.avengers.capstone.hostelrenting.exception.EntityNotFoundException;
import org.avengers.capstone.hostelrenting.exception.GenericException;
import org.avengers.capstone.hostelrenting.model.Room;
import org.avengers.capstone.hostelrenting.model.Type;
import org.avengers.capstone.hostelrenting.repository.RoomRepository;
import org.avengers.capstone.hostelrenting.service.RoomService;
import org.avengers.capstone.hostelrenting.service.TypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
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
        List<RoomDTOResponse> rooms = existedType.getRooms()
                .stream()
                .map(hostelRoom -> modelMapper.map(hostelRoom, RoomDTOResponse.class))
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
    public ResponseEntity<ApiSuccess> createRoomsByTypeId(@PathVariable Integer typeId,
                                                          @Valid @RequestBody Collection<RoomDTOCreate> reqDTOs) throws DuplicateKeyException {
        // reqDTOs is empty
        if (reqDTOs.isEmpty()){
            ApiSuccess<?> apiSuccess = new ApiSuccess<>(null,"There is no created room");
            return ResponseEntity.status(HttpStatus.OK).body(apiSuccess);
        }
        Collection<RoomDTOResponse> resDTOs = createRooms(typeId, reqDTOs);
        ApiSuccess<?> apiSuccess = new ApiSuccess<>(resDTOs, "Rooms has been created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(apiSuccess);
    }

    @PutMapping("/types/{typeId}/rooms/{roomId}")
    public ResponseEntity<ApiSuccess> updateDistrict(@PathVariable Integer typeId,
                                                     @PathVariable Integer roomId,
                                                     @Valid @RequestBody RoomDTOCreate rqRoom) throws EntityNotFoundException {
        // check that id exist or not
        Type existedType = typeService.findById(typeId);
        Room existedRoom = roomService.findById(roomId);

        rqRoom.setRoomId(existedRoom.getRoomId());
        Room rqModel = modelMapper.map(rqRoom, Room.class);
        rqModel.setType(existedType);
        Room updatedModel = roomService.save(rqModel);
        RoomDTOCreate resDTO = modelMapper.map(updatedModel, RoomDTOCreate.class);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiSuccess(resDTO, String.format(UPDATE_SUCCESS, Room.class.getSimpleName())));
    }


    private Collection<RoomDTOResponse> createRooms(Integer typeId, Collection<RoomDTOCreate> reqDTOs){
        // check existed Type
        Type exType = typeService.findById(typeId);

        // map dto -> model
        Collection<Room> reqModels = reqDTOs.stream().map(reqDTO -> {
            Room reqModel = modelMapper.map(reqDTO, Room.class);
            reqModel.setType(exType);
            return reqModel;
        }).collect(Collectors.toList());

        // check all rooms not exist
        Room violatedRoom = roomService.getExistedRoomInList(reqModels);
        if (violatedRoom!= null){
            throw new GenericException(Room.class, "is existed with", "roomName", violatedRoom.getRoomName());
        }

        // save list of model
        Collection<RoomDTOResponse> resDTOs = reqModels
                .stream()
                .map(reqModel -> {
                    Room resModel = roomService.save(reqModel);
                    RoomDTOResponse resDTO = modelMapper.map(resModel, RoomDTOResponse.class);
                    return resDTO;
                }).collect(Collectors.toList());

        return resDTOs;
    }

}
