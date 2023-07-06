package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.controller.dto.booking.BookingDTO;
import com.marcominaudo.gymweb.controller.dto.booking.BookingMapper;
import com.marcominaudo.gymweb.controller.dto.booking.SearchBookingDTO;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class BookingMapperTest {

    UtilsTest utilsTest = new UtilsTest();

    BookingMapper bookingMapper = new BookingMapper();

    @Test
    void toDTO(){
        // Booking data
        long id= 1;
        LocalDateTime subscriptionTime = LocalDateTime.now();
        LocalDateTime startTime= LocalDateTime.now();
        LocalDateTime endTime= LocalDateTime.now().plusMonths(4);
        User user = utilsTest.getUser();
        Room room = utilsTest.getRoom("Stanza 1", true);

        // Mapping to dto
        Booking booking = new Booking(id, subscriptionTime, startTime, endTime, user, room);
        BookingDTO bookingDTO = bookingMapper.toDTO(booking);

        // Test
        assertEquals(subscriptionTime, bookingDTO.getSubscriptionTime());
        assertEquals(startTime, bookingDTO.getStartTime());
        assertEquals(endTime, bookingDTO.getEndTime());
        assertEquals(user.getSurname(), bookingDTO.getSurname());
        assertEquals(user.getName(), bookingDTO.getName());
        assertEquals(room.getId(), bookingDTO.getRoomId());
    }

    @Test
    void toDTOUserNull(){
        // Booking data
        long id= 1;
        LocalDateTime subscriptionTime = LocalDateTime.now();
        LocalDateTime startTime= LocalDateTime.now();
        LocalDateTime endTime= LocalDateTime.now().plusMonths(4);
        Room room = utilsTest.getRoom("Stanza 1", true);

        // Mapping to dto
        Booking booking = new Booking(id, subscriptionTime, startTime, endTime, null, room);
        BookingDTO bookingDTO = bookingMapper.toDTO(booking);

        // Test
        assertNull(bookingDTO.getSurname());
        assertNull(bookingDTO.getName());
    }

    @Test
    void toDTOPage(){
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        bookings.add(new Booking());

        Page<Booking> page = new PageImpl<>(bookings);
        SearchBookingDTO searchBookingDTO = bookingMapper.toDTO(page);

        assertEquals(1 ,searchBookingDTO.getTotalPages());
        assertEquals(2 ,searchBookingDTO.getTotalElements());
    }

    @Test
    void toBooking(){
        // Booking data
        long id= 1;
        LocalDateTime subscriptionTime = LocalDateTime.now();
        LocalDateTime startTime= LocalDateTime.now();
        LocalDateTime endTime= LocalDateTime.now().plusMonths(4);
        User user = utilsTest.getUser();
        Room room = utilsTest.getRoom("Stanza 1", true);

        // Mapping to booking
        BookingDTO bookingDTO = new BookingDTO(subscriptionTime, startTime, endTime, room.getId(), "name", "surname");
        Booking booking = bookingMapper.toBooking(bookingDTO);

        // Test
        assertEquals(startTime, booking.getStartTime());
        assertEquals(endTime, booking.getEndTime());
        assertEquals(subscriptionTime, booking.getSubscriptionTime());
        assertNull(booking.getRoom());
        assertNull(booking.getUser());
    }
}
