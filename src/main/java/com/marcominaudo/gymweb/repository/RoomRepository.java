package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByIsActive(boolean isActive);

}
