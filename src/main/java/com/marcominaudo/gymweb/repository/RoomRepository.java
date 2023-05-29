package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
