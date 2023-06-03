package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.room.RoomDTO;
import com.marcominaudo.gymweb.controller.dto.room.RoomMapper;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.security.customAnnotation.Admin;
import com.marcominaudo.gymweb.security.customAnnotation.All;
import com.marcominaudo.gymweb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Admin
    @PostMapping()
    public ResponseEntity<RoomDTO> room(@RequestBody RoomDTO roomDTO) throws RoomException {
        Room room = roomMapper.DTOToRoom(roomDTO);
        Room roomDB = roomService.createRoom(room);
        RoomDTO response = roomMapper.RoomToDTO(roomDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Update room
    * */
    @Admin
    @PostMapping("/update")
    public ResponseEntity<RoomDTO> roomUpdate(@RequestBody RoomDTO roomDTO) throws RoomException {
        Room room = roomMapper.DTOToRoom(roomDTO);
        Room roomDB = roomService.updateRoom(room);
        RoomDTO response = roomMapper.RoomToDTO(roomDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Get all rooms
    * */
    @All
    @GetMapping("/all")
    public ResponseEntity<List<RoomDTO>> allRooms()  {
        List<Room> rooms = roomService.allRooms();
        List<RoomDTO> response = rooms.stream().map(room -> roomMapper.RoomToDTO(room)).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
