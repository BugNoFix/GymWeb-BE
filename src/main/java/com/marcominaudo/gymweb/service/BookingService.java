package com.marcominaudo.gymweb.service;

import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.builder.BookingBuilder;
import com.marcominaudo.gymweb.repository.BookingRepository;
import com.marcominaudo.gymweb.utilis.object.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        // Check id date is valis
        if(startDate.isAfter(endDate))
            throw new BookingException("Start time is after end time");
        if(startDate.isBefore(LocalDateTime.now()))
            throw new BookingException("Date invalid");

        // Check if the slot time is valid
        int startMinute = startDate.getMinute();
        int endMinute = endDate.getMinute();
        if(startMinute % 15 != 0 || endMinute % 15 != 0)
            throw new BookingException("Minute slot is invalid");

        // Check is room is valid
        roomService.roomIsValid(roomId);

        // Get room
        Room room = roomService.getRoom(roomId);

        // Check if the number of user is greater of room size
        List<Booking> bookings = bookingRepository.findAllBetweenBookingDate(startDate, endDate, roomId);
        // Create map for count number of user booked in one slot time (15 minutes)
        Map<LocalDateTime, Integer> slots = new HashMap<>();
        int customers;
        AtomicReference<LocalDateTime> slot = new AtomicReference<>(startDate);
        for(; slot.get().isBefore(endDate); slot.set(slot.get().plusMinutes(15))){
            // Count number of user in slot time
            customers = (int) bookings.stream().filter(b -> dateIsAfterOrEqual(slot.get(),b.getStartTime()) && dateIsBeforeOrEqual(slot.get().plusMinutes(15),b.getEndTime())).count();
            slots.put(slot.get(), customers);
        }
        // Check if for each slot time the users is minor of room size
        List<LocalDateTime> timeSlotFull = slots.keySet().stream()
                .filter(key -> slots.get(key) >= room.getSize()) // Get time slot when the room is full
                .toList();
        if(!timeSlotFull.isEmpty())
            throw new BookingException("Booking over room limit", timeSlotFull);

        // Check if the user has a booking in the same period

        User user = utils.getUser();
        long bookingsOfUser = bookingRepository.findAllByUserIdAndBetweenBookingDate(user.getId(), startDate, endDate);
        if(bookingsOfUser > 0)
            throw new BookingException("User is already booked");

        return true;


    }

    private boolean dateIsAfterOrEqual(LocalDateTime date1, LocalDateTime date2){
        if(date1.isAfter(date2))
            return true;
        if(date1.isEqual(date2))
            return true;
        return false;
    }

    private boolean dateIsBeforeOrEqual(LocalDateTime date1, LocalDateTime date2){
        if(date1.isBefore(date2))
            return true;
        if(date1.isEqual(date2))
            return true;
        return false;
    }

    public Map<Shift, List<String>> bookingInfo(long roomId, LocalDateTime day, Role role) {
        List<Booking> bookings = bookingRepository.findByRoomIdAndDay(roomId, day);
        List<Booking> bookingPts = bookings.stream().filter(b -> b.getUser().getRole() == role).toList();

        Map<Shift, List<String>> shifts = new LinkedHashMap<>();
        bookingPts.stream().forEach(b -> {
            Shift workShift = new Shift(b.getStartTime(), b.getEndTime());
            shifts.put(workShift, Arrays.asList(b.getUser().getName(), b.getUser().getSurname()));
        });
        return shifts;
    }

    public Page<Booking> bookingOfCustomer(String uuidCustomer, int size, int page, LocalDateTime day) throws UserException {
        User customer = utils.getUserByUuid(uuidCustomer);
        Pageable pageSetting = PageRequest.of(page, size);
        return bookingRepository.findAllBookingOfUserInOneDay(customer.getId(), pageSetting, day);
    }
}
