package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b join fetch b.room WHERE b.startTime >= :startDate and b.endTime <= :endDate and b.room.id = :roomId")
    List<Booking> findAllBetweenBookingDate(LocalDateTime startDate, LocalDateTime endDate, long roomId);
}