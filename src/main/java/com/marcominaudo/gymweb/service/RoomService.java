package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;


    public boolean roomIsValid(long roomId) throws RoomException {
        Room room = getRoom(roomId);
        if(!room.isActive())
            throw new RoomException(RoomException.ExceptionCodes.INACTIVE_ROOM);
        return true;
    }

    public Room getRoom(long roomId) throws RoomException {
        return roomRepository.findById(roomId).orElseThrow(()-> new RoomException(RoomException.ExceptionCodes.ROOM_NOT_EXIST));
    }

    public Room createRoom(Room room) throws RoomException {
        if(room.getSize() <= 0 )
            throw new RoomException(RoomException.ExceptionCodes.SIZE_NOT_VALID);
        if(room.getName().isEmpty())
            throw new RoomException(RoomException.ExceptionCodes.MISSING_DATA);
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) throws RoomException {
        Room roomDB = roomRepository.findById(room.getId()).orElseThrow(() ->new RoomException(RoomException.ExceptionCodes.INVALID_ROOM));
        roomDB.setActive(room.isActive());
        roomDB.setName(room.getName());
        roomDB.setSize(room.getSize());
        return roomRepository.save(roomDB);

    }

    public Page<Room> allRooms(int page, int size) {
        Pageable pageSetting = PageRequest.of(page, size, Sort.by("id").descending());
        return roomRepository.findAll(pageSetting);
    }




}
