package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.room.RoomDTO;
import com.marcominaudo.gymweb.controller.dto.room.RoomMapper;
import com.marcominaudo.gymweb.controller.dto.room.SearchRoomDTO;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyAdminAccess;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    RoomMapper roomMapper;

    /*
    * Create room
    * */
    @OnlyAdminAccess
    @PostMapping()
    public ResponseEntity<RoomDTO> room(@RequestBody RoomDTO roomDTO) throws RoomException {
        Room room = roomMapper.DTOToRoom(roomDTO);
        Room roomDB = roomService.createRoom(room);
        RoomDTO response = roomMapper.roomToDTO(roomDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Update room
    * */
    @OnlyAdminAccess
    @PostMapping("/update")
    public ResponseEntity<RoomDTO> roomUpdate(@RequestBody RoomDTO roomDTO) throws RoomException {
        Room room = roomMapper.DTOToRoom(roomDTO);
        Room roomDB = roomService.updateRoom(room);
        RoomDTO response = roomMapper.roomToDTO(roomDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get all rooms
    * */
    @FreeAccess
    @GetMapping("/all")
    public ResponseEntity<SearchRoomDTO> allRooms(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size)  {
        Page<Room> searchInfo = roomService.allRooms(page, size);
        SearchRoomDTO response = roomMapper.roomsToDTO(searchInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
