package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.builder.BookingBuilder;
import com.marcominaudo.gymweb.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookingService {
    @Autowired
    RoomService roomService;

    @Autowired
    Utils utils;

    @Autowired
    BookingRepository bookingRepository;
    public Booking newBooking(Booking booking, long idRoom) throws BookingException, RoomException {
        bookingValid(booking.getStartTime(), booking.getEndTime(), idRoom);
        Booking newBooking = new BookingBuilder().builder()
                .room(roomService.getRoom(idRoom))
                .user(utils.getUser())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .build();
        return bookingRepository.save(newBooking);
    }

    private boolean bookingValid(LocalDateTime startDate, LocalDateTime endDate, long roomId) throws BookingException, RoomException {
        if(startDate.isAfter(endDate))
            throw new BookingException("Start time is after end time");

        if(startDate.isBefore(LocalDateTime.now()))
            throw new BookingException("Date invalid");

        int startMinute = startDate.getMinute();
        int endMinute = endDate.getMinute();
        if(startMinute % 15 != 0 || endMinute % 15 != 0)
            throw new BookingException("Minute slot is invalid");

        roomService.RoomIsValid(roomId);

        List<Booking> bookings = bookingRepository.findAllBetweenBookingDate(startDate, endDate, roomId);
        Map<LocalDateTime, Integer> slots = new HashMap<>();
        int customers;
        AtomicReference<LocalDateTime> slot = new AtomicReference<>(startDate);
        for(; slot.get().isBefore(endDate); slot.set(slot.get().plusMinutes(15))){
            customers = (int) bookings.stream().filter(b -> dateIsAfterOrEqual(slot.get(),b.getStartTime()) && dateIsAfterOrEqual(slot.get().plusMinutes(15),b.getEndTime())).count();
            slots.put(slot.get(), customers);
        }
        List<Booking> incompatibleBookings = bookings.stream().filter(b -> b.getRoom().getSize() <= slots.get(b.getStartTime())).toList();
        if(!incompatibleBookings.isEmpty())
            throw new BookingException("Booking over room limit" + incompatibleBookings);
        return true;
    }

    private boolean dateIsAfterOrEqual(LocalDateTime date1, LocalDateTime date2){
        if(date1.isAfter(date1))
            return true;
        if(date2.isAfter(date2))
            return true;
        if(date1.isEqual(date1))
            return true;
        if(date1.isEqual(date1))
            return true;
        return false;
    }
}
