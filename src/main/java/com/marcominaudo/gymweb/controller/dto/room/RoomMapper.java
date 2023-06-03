package com.marcominaudo.gymweb.controller.dto.room;

import com.marcominaudo.gymweb.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomDTO RoomToDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setSize(room.getSize());
        roomDTO.setName(room.getName());
        roomDTO.setActive(room.isActive());
        return roomDTO;
    }

    public Room DTOToRoom(RoomDTO roomDTO){
        Room room = new Room();
        room.setActive(roomDTO.isActive());
        room.setSize(roomDTO.getSize());
        room.setName(roomDTO.getName());
        return room;
    }

}
