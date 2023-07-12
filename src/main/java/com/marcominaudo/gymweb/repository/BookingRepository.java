package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b join fetch b.room WHERE ((:startDate >= b.startTime and :startDate < b.endTime) or (:endDate > b.startTime and :endDate <= b.endTime)or (:startDate <= b.startTime and  :endDate >= b.endTime)) and  b.room.id = :roomId")
    List<Booking> findAllBetweenBookingDate(LocalDateTime startDate, LocalDateTime endDate, long roomId);

    //@Query("SELECT b FROM Booking b WHERE :roomId = b.room.id AND CAST(b.startTime AS date) = CAST(:day AS date) order by b.startTime asc")
    //List<Booking> findByRoomIdAndDay(long roomId, LocalDateTime day);

    @Query("SELECT b FROM Booking b WHERE :roomId = b.room.id AND b.user.role = 'CUSTOMER' AND ((:startDateTime >= b.startTime and :startDateTime < b.endTime) or (:endDateTime > b.startTime and :endDateTime <= b.endTime)or (:startDateTime <= b.startTime and  :endDateTime >= b.endTime)) order by b.startTime asc")
    List<Booking> findCustomerByRoomIdAndDay(long roomId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT b FROM Booking b WHERE :roomId = b.room.id AND b.user.role = 'PT' AND ((:startDateTime >= b.startTime and :startDateTime < b.endTime) or (:endDateTime > b.startTime and :endDateTime <= b.endTime)or (:startDateTime <= b.startTime and  :endDateTime >= b.endTime)) order by b.startTime asc")
    List<Booking> findPtByRoomIdAndDay(long roomId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query("SELECT b FROM Booking b WHERE :customerId = b.user.id AND CAST(b.startTime AS date) = CAST(:day AS date) order by b.startTime asc")
    Page<Booking> findAllBookingOfUserInOneDay(long customerId, Pageable pageSetting, LocalDateTime day);

    //@Query("SELECT count(b) FROM Booking b WHERE b.startTime >= :startDate and b.endTime <= :endDate and :userId = b.user.id")
    @Query("SELECT count(b) FROM Booking b WHERE ((:startDate >= b.startTime and :startDate < b.endTime) or (:endDate > b.startTime and :endDate <= b.endTime)or (:startDate <= b.startTime and  :endDate >= b.endTime)) and :userId = b.user.id")
    long findAllByUserIdAndBetweenBookingDate(long userId, LocalDateTime startDate, LocalDateTime endDate);
}
