package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.controller.dto.room.RoomDTO;
import com.marcominaudo.gymweb.controller.dto.room.RoomMapper;
import com.marcominaudo.gymweb.controller.dto.room.SearchRoomDTO;
import com.marcominaudo.gymweb.model.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RoomMapperTest {

    RoomMapper roomMapper = new RoomMapper();

    @Test
    void toDTO(){
        // Room data
        long id = 1;
        String name = "Stanza 1";
        boolean isActive = true;
        int size = 3;

        // Mapping to dto
        Room room = new Room(id, name, isActive, size);
        RoomDTO roomDTO = roomMapper.toDTO(room);

        // Test
        assertEquals(id, roomDTO.getId());
        assertEquals(name, roomDTO.getName());
        assertEquals(isActive, roomDTO.isActive());
        assertEquals(size, roomDTO.getSize());
    }

    @Test
    void toDTOPage(){
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room());
        rooms.add(new Room());

        Page<Room> page = new PageImpl<>(rooms);
        SearchRoomDTO searchRoomDTO = roomMapper.toDTO(page);

        assertEquals(1, searchRoomDTO.getTotalPages());
        assertEquals(2, searchRoomDTO.getTotalElements());
    }

    @Test
    void toRoom(){
        // Room data
        long id = 1;
        String name = "Stanza 1";
        boolean isActive = true;
        int size = 3;

        // Mapping to dto
        RoomDTO roomDTO = new RoomDTO(name, isActive, size, id);
        Room room = roomMapper.toRoom(roomDTO);

        // Test
        assertEquals(id, room.getId());
        assertEquals(name, room.getName());
        assertEquals(isActive, room.isActive());
        assertEquals(size, room.getSize());
    }
}
