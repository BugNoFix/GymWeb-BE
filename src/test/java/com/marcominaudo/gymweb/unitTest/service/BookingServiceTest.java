package com.marcominaudo.gymweb.unitTest.service;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.BookingRepository;
import com.marcominaudo.gymweb.service.BookingService;
import com.marcominaudo.gymweb.service.RoomService;
import com.marcominaudo.gymweb.service.Utils;
import com.marcominaudo.gymweb.service.bookingSearch.BookingSearchCustomer;
import com.marcominaudo.gymweb.service.bookingSearch.BookingSearchPt;
import com.marcominaudo.gymweb.service.bookingSearch.BookingStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    RoomService roomService;

    @Mock
    Utils utils;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    BookingStrategyFactory bookingStrategyFactory;

    @InjectMocks
    BookingService bookingService;

    LocalDateTime today = LocalDateTime.now().plusYears(1).withHour(6).withMinute(0);

    UtilsTest utilsTest = new UtilsTest(today);

    // --------- New Booking ----------
    @Test
    void bookingConfirm() throws RoomException {
        // Room mock
        when(roomService.getRoom(any(Long.class))).thenReturn(utilsTest.getRoom("crossfit", true));
        when(roomService.roomIsValid(any(Long.class))).thenReturn(true);

        //Utils mock
        when(utils.getLoggedUser()).thenReturn(utilsTest.getUser());

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
    void bookingThrowMissingData(){
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(new Booking(), 1));
        assertEquals(BookingException.ExceptionCodes.MISSING_DATA.name(), thrown.getMessage());
    }

    @Test
    void bookingThrowInvalidDate() {
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
    void bookingThrowInvalidTimeSlot() {
        // Minutes slots are invalid
        Booking booking = utilsTest.getBooking(0, 12, utilsTest.getUser());
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking, 1));
        assertEquals(BookingException.ExceptionCodes.INVALID_TIME_RANGE.name(), thrown.getMessage());
    }

    @Test
    void bookingThrowRoomIsFull() throws RoomException {
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
    void bookingThrowUserAlreadyBooked() throws RoomException {
        // Room mock
        when(roomService.getRoom(any(Long.class))).thenReturn(utilsTest.getRoom("crossfit", true));
        when(roomService.roomIsValid(any(Long.class))).thenReturn(true);

        // Booking repository mock
        when(bookingRepository.findAllByUserIdAndBetweenBookingDate(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(1L);

        // Utils mock
        when(utils.getLoggedUser()).thenReturn(utilsTest.getUser());

        // Test: User already booked in the same period
        Booking booking = utilsTest.getBooking(0, 30, utilsTest.getUser());
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.newBooking(booking, 1));
        assertEquals(BookingException.ExceptionCodes.USER_ALREADY_BOOKED.name(), thrown.getMessage());
    }
    // ----------------------------------------

    // --------- Info Booking ----------
    @Test
    void bookingInfoPt(){
        // Mock
        List<Booking> bookings = utilsTest.get3Booking();
        bookings.get(0).getUser().setRole(Role.PT);
        bookings.get(1).getUser().setRole(Role.PT);
        bookings.get(1).getUser().setRole(Role.PT);
        when(bookingRepository.findPtByRoomIdAndDay(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(bookings);
        when(bookingStrategyFactory.getStrategy(any())).thenReturn(new BookingSearchPt(bookingRepository));

        // Test: get all booking of pt
        List<Booking> result = assertDoesNotThrow(() -> bookingService.bookingInfo(1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), Role.CUSTOMER));
        assertEquals(3, result.size());
    }

    @Test
    void bookingInfoThrowMissingData(){
        // Data
        List<Booking> bookings = utilsTest.get3Booking();
        bookings.get(0).getUser().setRole(Role.PT);
        bookings.get(1).getUser().setRole(Role.PT);
        bookings.get(2).getUser().setRole(Role.CUSTOMER);

        // Test: get all booking of pt
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.bookingInfo(1, null, null, Role.PT));
        assertEquals(BookingException.ExceptionCodes.MISSING_DATA.name(), thrown.getMessage());

    }

    @Test
    void bookingInfoCustomer() {
        // Mock
        List<Booking> bookings = utilsTest.get3Booking();
        bookings.get(0).getUser().setRole(Role.CUSTOMER);
        bookings.get(1).getUser().setRole(Role.CUSTOMER);
        bookings.get(2).getUser().setRole(Role.CUSTOMER);
        when(bookingRepository.findCustomerByRoomIdAndDay(any(Long.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(bookings);
        when(bookingStrategyFactory.getStrategy(any())).thenReturn(new BookingSearchCustomer(bookingRepository));

        // Test: get all booking of customer
        List<Booking> result = assertDoesNotThrow(() -> bookingService.bookingInfo(1, LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), Role.PT));
        assertEquals(3, result.size());
    }
    // ----------------------------------------

    // --------- Other ----------
    @Test
    void bookingOfCustomer() throws UserException {
        // Mock
        when(bookingRepository.findAllBookingOfUserInOneDay(any(Long.class), any(Pageable.class), any(LocalDateTime.class))).thenReturn(Page.empty());
        User customer = utilsTest.getCustomer("Marco", "Minaudo", "marco@gmail.com", true, null);
        when(utils.getUserByUuid(any(String.class))).thenReturn(customer);


        // Test: get all booking of customer
        assertAll(()-> bookingService.bookingOfCustomer("uuidCustomer", 5, 0, LocalDateTime.now()));
    }
    // ----------------------------------------


}
