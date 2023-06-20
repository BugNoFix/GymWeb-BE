package com.marcominaudo.gymweb.controller.dto.room;

import com.marcominaudo.gymweb.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomMapper {
    public RoomDTO toDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setSize(room.getSize());
        roomDTO.setName(room.getName());
        roomDTO.setActive(room.isActive());
        roomDTO.setId(room.getId());
        return roomDTO;
    }

    public SearchRoomDTO toDTO(Page<Room> searchInfo){
        SearchRoomDTO searchRoomDTO = new SearchRoomDTO();
        List<RoomDTO> rooms = searchInfo.getContent().stream().map(this::toDTO).toList(); // Covert each room in roomDTO

        searchRoomDTO.setRooms(rooms);
        searchRoomDTO.setTotalPages(searchInfo.getTotalPages());
        searchRoomDTO.setTotalElements(searchInfo.getTotalElements());
        return searchRoomDTO;
    }

    public Room toRoom(RoomDTO roomDTO){
        Room room = new Room();
        room.setId(roomDTO.getId());
        room.setActive(roomDTO.isActive());
        room.setSize(roomDTO.getSize());
        room.setName(roomDTO.getName());
        return room;
    }

}
