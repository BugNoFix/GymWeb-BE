package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<Room> room(@RequestBody Room room) throws RoomException {
        Room response = roomService.createRoom(room);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/room/update")
    public ResponseEntity<Room> roomUpdate(@RequestBody Room room) throws RoomException {
        Room response = roomService.updateRoom(room);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
