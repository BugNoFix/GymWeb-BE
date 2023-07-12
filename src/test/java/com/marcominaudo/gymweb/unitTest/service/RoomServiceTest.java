package com.marcominaudo.gymweb.unitTest.service;

import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.repository.RoomRepository;
import com.marcominaudo.gymweb.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomService roomService;

    // --------- Create room ----------
    @Test
    void roomThrowSizeNotValid() {
        Room room = new Room(1, "room 1", true, 0);
        RoomException thrown = assertThrows(RoomException.class, () -> roomService.createRoom(room));
        assertEquals(RoomException.ExceptionCodes.SIZE_NOT_VALID.name(), thrown.getMessage());
    }

    @Test
    void roomThrowMissingName() {
        Room room = new Room(1, null, true, 2);
        RoomException thrown = assertThrows(RoomException.class, () -> roomService.createRoom(room));
        assertEquals(RoomException.ExceptionCodes.MISSING_DATA.name(), thrown.getMessage());
    }

    @Test
    void roomCreated() {
        Room room = new Room(1, "room 1", true, 2);
        when(roomRepository.save(any(Room.class))).then(returnsFirstArg());
        assertAll(() -> roomService.createRoom(room));
    }
    // ----------------------------------------

    // --------- Room is valid ----------
    @Test
    void roomThrowInactiveRoom() {
        // Mock
        Optional<Room> room = Optional.of( new Room(1, "room 1", false, 3));
        when(roomRepository.findById(any(Long.class))).thenReturn(room);

        // Test
        RoomException thrown = assertThrows(RoomException.class, () -> roomService.roomIsValid(1));
        assertEquals(RoomException.ExceptionCodes.INACTIVE_ROOM.name(), thrown.getMessage());
    }

    @Test
    void roomIsValid() {
        // Mock
        Optional<Room> room = Optional.of( new Room(1, "room 1", true, 3));
        when(roomRepository.findById(any(Long.class))).thenReturn(room);

        // Test
        assertAll(() -> roomService.roomIsValid(1));
    }
    // ----------------------------------------

    // --------- Other ----------
    @Test
    void roomUpdate() {
        // Mock
        Optional<Room> room = Optional.of( new Room(1, "room 1", false, 3));
        when(roomRepository.findById(any(Long.class))).thenReturn(room);

        // Test
        assertAll(() -> roomService.updateRoom(room.get()));
    }

    @Test
    void allRoom() {
        // Mock
        when(roomRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        // Test
        assertEquals(Page.empty(), roomService.allRooms(0, 5));
    }
    // ----------------------------------------

}
