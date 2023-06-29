package com.marcominaudo.gymweb.unitTest;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.repository.BookingRepository;
import com.marcominaudo.gymweb.service.BookingService;
import com.marcominaudo.gymweb.service.RoomService;
import com.marcominaudo.gymweb.service.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//Path coverage
public class BookingTest {

    @Mock
    RoomService roomService;

    @Mock
    Utils utils;

    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    BookingService bookingService;

    LocalDateTime today = LocalDateTime.now().plusYears(1).withHour(6).withMinute(0);
    UtilsTest utilsTest = new UtilsTest(today);

    @Test
    void bookingConfirm() throws RoomException {
        // Room mock
        when(roomService.getRoom(any(Long.class))).thenReturn(utilsTest.getRoom("crossfit", true));
        when(roomService.roomIsValid(any(Long.class))).thenReturn(true);

        //Utils mock
        when(utils.getUser()).thenReturn(utilsTest.getUser());

        //Booking repository mock
        when(bookingRepository.findAllBetweenBookingDate(any(LocalDateTime.class), any(LocalDateTime.class), any(Long.class)))
                .thenReturn(utilsTest.get2Booking());
        when(bookingRepository.findAllByUserIdAndBetweenBookingDate(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(0L);
        when(bookingRepository.save(any(Booking.class))).then(returnsFirstArg());

        // Test
        Booking booking = utilsTest.getBooking(0, 45, utilsTest.getUser());
        assertAll(() -> bookingService.newBooking(booking, 1));
    }

    @Test
    void bookingInvalidDate() {
        // Start date is after end date
        Booking booking = utilsTest.getBooking(50, 0, utilsTest.getUser());
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking, 1));
        assertEquals(BookingException.ExceptionCodes.INVALID_DATE_RANGE.name(), thrown.getMessage());

        // Start date is after now
        Booking booking2 = utilsTest.getBooking(LocalDateTime.MIN, today, utilsTest.getUser());
        thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking2, 1));
        assertEquals(BookingException.ExceptionCodes.INVALID_DATE_RANGE.name(), thrown.getMessage());
    }

    @Test
    void bookingExInvalidTimeSlot() {
        // Minutes slots are invalid
        Booking booking = utilsTest.getBooking(0, 12, utilsTest.getUser());
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking, 1));
        assertEquals(BookingException.ExceptionCodes.INVALID_TIME_RANGE.name(), thrown.getMessage());
    }

    @Test
    void bookingExRoomIsFull() throws RoomException {
        // Room mock
        when(roomService.getRoom(any(Long.class))).thenReturn(utilsTest.getRoom("crossfit", true));
        when(roomService.roomIsValid(any(Long.class))).thenReturn(true);
        // Booking repository mock
        when(bookingRepository.findAllBetweenBookingDate(any(LocalDateTime.class), any(LocalDateTime.class), any(Long.class)))
                .thenReturn(utilsTest.get3Booking());

        // Test: Room is full
        Booking booking = utilsTest.getBooking(0, 30, utilsTest.getUser());
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking, 1));
        assertEquals(BookingException.ExceptionCodes.ROOM_IS_FULL.name(), thrown.getMessage());
    }

    @Test
    void bookingExUserAlreadyBooked() throws RoomException {
        // Room mock
        when(roomService.getRoom(any(Long.class))).thenReturn(utilsTest.getRoom("crossfit", true));
        when(roomService.roomIsValid(any(Long.class))).thenReturn(true);

        // Booking repository mock
        when(bookingRepository.findAllByUserIdAndBetweenBookingDate(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1L);

        // Utils mock
        when(utils.getUser()).thenReturn(utilsTest.getUser());

        // Test: User already booked in the same period
        Booking booking = utilsTest.getBooking(0, 30, utilsTest.getUser());
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking, 1));
        assertEquals(BookingException.ExceptionCodes.USER_ALREADY_BOOKED.name(), thrown.getMessage());
    }

}
