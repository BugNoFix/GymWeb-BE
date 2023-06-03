package com.marcominaudo.gymweb.controller.dto.room;

import com.marcominaudo.gymweb.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMapper {
    public RoomDTO roomToDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setSize(room.getSize());
        roomDTO.setName(room.getName());
        roomDTO.setActive(room.isActive());
        return roomDTO;
    }

    public SearchRoomDTO roomsToDTO(Page<Room> searchInfo){
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        List<RoomDTO> rooms = searchInfo.getContent().stream().map(room -> roomToDTO(room)).toList();

        searchRoomDTO.setRooms(rooms);
        searchRoomDTO.setTotalPages(searchInfo.getTotalPages());
        searchRoomDTO.setTotalElements(searchInfo.getTotalElements());
        return searchRoomDTO;
    }

    public Room DTOToRoom(RoomDTO roomDTO){
        Room room = new Room();
        room.setActive(roomDTO.isActive());
        room.setSize(roomDTO.getSize());
        room.setName(roomDTO.getName());
        return room;
    }

}
