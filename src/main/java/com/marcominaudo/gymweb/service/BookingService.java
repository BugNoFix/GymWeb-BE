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
import com.marcominaudo.gymweb.service.bookingSearch.BookingSearchType;
import com.marcominaudo.gymweb.service.bookingSearch.BookingStrategyFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.marcominaudo.gymweb.exception.exceptions.BookingException.ExceptionCodes.MISSING_DATA;


@Service
@AllArgsConstructor
public class BookingService {
    @Autowired
    RoomService roomService;

    @Autowired
    Utils utils;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingStrategyFactory bookingStrategyFactory;

    public Booking newBooking(Booking booking, long idRoom) throws BookingException, RoomException {
        bookingValid(booking.getStartTime(), booking.getEndTime(), idRoom);
        Booking newBooking = new BookingBuilder().builder()
                .room(roomService.getRoom(idRoom))
                .user(utils.getLoggedUser())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .build();
        return bookingRepository.save(newBooking);
    }

    private void bookingValid(LocalDateTime startDate, LocalDateTime endDate, long roomId) throws BookingException, RoomException {
        //Check data
        if(startDate == null || endDate == null)
            throw new BookingException(MISSING_DATA);

        // Check id date is valid
        if(startDate.isAfter(endDate) || startDate.isBefore(LocalDateTime.now()))
            throw new BookingException(BookingException.ExceptionCodes.INVALID_DATE_RANGE);

        // Check if the slot time is valid
        int startMinute = startDate.getMinute();
        int endMinute = endDate.getMinute();
        if(startMinute % 15 != 0 || endMinute % 15 != 0)
            throw new BookingException(BookingException.ExceptionCodes.INVALID_TIME_RANGE);

        // Check if room is valid
        roomService.roomIsValid(roomId);

        // Get room
        Room room = roomService.getRoom(roomId);

        // Get bookings for check if the number of user is greater of room size
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
        // Check if for each slot time the user is minor of room size
        List<LocalDateTime> timeSlotFull = slots.keySet().stream()
                .filter(key -> slots.get(key) >= room.getSize()) // Get time slot when the room is full
                .toList();
        if(!timeSlotFull.isEmpty())
            throw new BookingException(BookingException.ExceptionCodes.ROOM_IS_FULL, timeSlotFull);

        // Check if the user has a booking in the same period
        User user = utils.getLoggedUser();
        long bookingsOfUser = bookingRepository.findAllByUserIdAndBetweenBookingDate(user.getId(), startDate, endDate);
        if(bookingsOfUser > 0)
            throw new BookingException(BookingException.ExceptionCodes.USER_ALREADY_BOOKED);

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


    //Get user booked in one day
    public List<Booking> bookingInfo(long roomId, LocalDateTime startDatetime, LocalDateTime endDatetime, Role roleSearch) throws BookingException {
        if (startDatetime == null || endDatetime == null)
            throw new BookingException(MISSING_DATA);
        if(roleSearch.equals(Role.PT))
            return bookingStrategyFactory.getStrategy(BookingSearchType.PT).search(roomId, startDatetime, endDatetime);
        else
            return bookingStrategyFactory.getStrategy(BookingSearchType.CUSTOMER).search(roomId, startDatetime, endDatetime);
    }

    public Page<Booking> bookingOfCustomer(String uuidCustomer, int size, int page, LocalDateTime day) throws UserException {
        User customer = utils.getUserByUuid(uuidCustomer);
        Pageable pageSetting = PageRequest.of(page, size);
        return bookingRepository.findAllBookingOfUserInOneDay(customer.getId(), pageSetting, day);
    }
}
