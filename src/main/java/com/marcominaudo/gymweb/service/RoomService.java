package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;


    public boolean RoomIsValid(long roomId) throws RoomException {
        Room room = getRoom(roomId);
        if(!room.isActive())
            new RoomException("Room not active");
        return true;
    }

    public Room getRoom(long roomId) throws RoomException {
        return roomRepository.findById(roomId).orElseThrow(()-> new RoomException("Room not exist"));
    }

    public Room createRoom(Room room) throws RoomException {
        if(room.getSize() <= 0 || room.getName().isEmpty()) //TODO: migliorare il messaggio di errore
            throw new RoomException("Missing data");
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) throws RoomException {
        Room roomDB = roomRepository.findById(room.getId()).orElseThrow(() ->new RoomException("Room not valid"));
        roomDB.setActive(room.isActive());
        roomDB.setName(room.getName());
        roomDB.setSize(room.getSize());
        return  roomRepository.save(roomDB);

    }

    public List<Room> allRooms() {
        return roomRepository.findByIsActive(true);
    }




}
