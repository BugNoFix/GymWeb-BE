package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.booking.BookingDTO;
import com.marcominaudo.gymweb.controller.dto.booking.BookingMapper;
import com.marcominaudo.gymweb.controller.dto.booking.SearchBookingDTO;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.security.customAnnotation.AdminAndPtAccess;
import com.marcominaudo.gymweb.security.customAnnotation.CustomerAndPtAccess;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyCustomerAccess;
import com.marcominaudo.gymweb.security.customAnnotation.OnlyPtAccess;
import com.marcominaudo.gymweb.service.BookingService;
import com.marcominaudo.gymweb.utilis.object.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingMapper bookingMapper;

    /*
    * Create a new booking
    * */
    @CustomerAndPtAccess
    @PostMapping()
    public ResponseEntity<BookingDTO> booking(@RequestBody BookingDTO bookingDTO) throws BookingException, RoomException {
        Booking booking = bookingMapper.DTOToBooking(bookingDTO);
        long roomId = bookingDTO.getRoomId();
        Booking bookingDB = bookingService.newBooking(booking, roomId);
        BookingDTO response = bookingMapper.bookingToDTO(bookingDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Gets all pt booked of one day
    * */
    @FreeAccess
    @PostMapping("/pt")
    public ResponseEntity<Map<Shift, String>> allPtBookingOfDay(@RequestBody BookingDTO bookingDTO) {
        LocalDateTime startTime = bookingDTO.getStartTime();
        long roomId = bookingDTO.getRoomId();
        Map<Shift, String> response = bookingService.bookingInfo(roomId, startTime, Role.PT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Gets all customer booked of one day
    * */
    @AdminAndPtAccess
    @PostMapping("/customers")
    public ResponseEntity<Map<Shift, String>> allCustomersBookingOfDay(@RequestBody BookingDTO bookingDTO) {
        LocalDateTime startTime = bookingDTO.getStartTime();
        long roomId = bookingDTO.getRoomId();
        Map<Shift, String> response = bookingService.bookingInfo(roomId, startTime, Role.CUSTOMER);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * get all booking of a user
    * */
    @AdminAndPtAccess
    @PostMapping("/{uuidCustomer}")
    public ResponseEntity<SearchBookingDTO> bookingOfCustomer(@RequestBody BookingDTO bookingDTO, @PathVariable("uuidCustomer") String uuidCustomer, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) {
        LocalDateTime day = bookingDTO.getStartTime();
        Page<Booking> bookingsDB = bookingService.bookingOfCustomer(uuidCustomer, size, page, day);
        SearchBookingDTO response = bookingMapper.bookingsToDTO(bookingsDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
