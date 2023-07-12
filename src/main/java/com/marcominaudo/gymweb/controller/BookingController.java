package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.booking.BookingDTO;
import com.marcominaudo.gymweb.controller.dto.booking.BookingMapper;
import com.marcominaudo.gymweb.controller.dto.booking.SearchBookingDTO;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.security.customAnnotation.AdminAndPtAccess;
import com.marcominaudo.gymweb.security.customAnnotation.CustomerAndPtAccess;
import com.marcominaudo.gymweb.security.customAnnotation.FreeAccess;
import com.marcominaudo.gymweb.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
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
        Booking booking = bookingMapper.toBooking(bookingDTO);
        long roomId = bookingDTO.getRoomId();
        Booking bookingDB = bookingService.newBooking(booking, roomId);
        BookingDTO response = bookingMapper.toDTO(bookingDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    * Gets all pt booked of one day
    * */
    @FreeAccess
    @PostMapping("/pt")
    public ResponseEntity<List<BookingDTO>> allPtBookingOfDay(@RequestBody BookingDTO bookingDTO) throws BookingException {
        LocalDateTime startTime = bookingDTO.getStartTime();
        long roomId = bookingDTO.getRoomId();
        List<Booking> booking = bookingService.bookingInfo(roomId, startTime, Role.PT);
        List<BookingDTO> bookingDTOs = booking.stream().map(b -> bookingMapper.toDTO(b)).toList();
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }

    /*
    * Gets all customer booked of one day
    * */
    @AdminAndPtAccess
    @PostMapping("/customers")
    public ResponseEntity<List<BookingDTO>> allCustomersBookingOfDay(@RequestBody BookingDTO bookingDTO) throws BookingException {
        LocalDateTime startTime = bookingDTO.getStartTime();
        long roomId = bookingDTO.getRoomId();
        List<Booking> booking = bookingService.bookingInfo(roomId, startTime, Role.CUSTOMER);
        List<BookingDTO> bookingDTOs = booking.stream().map(b -> bookingMapper.toDTO(b)).toList();
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }

    /*
    * get all booking of a user
    * */
    @AdminAndPtAccess
    @PostMapping("/{uuidCustomer}")
    public ResponseEntity<SearchBookingDTO> bookingOfCustomer(@RequestBody BookingDTO bookingDTO, @PathVariable("uuidCustomer") String uuidCustomer, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) throws UserException {
        LocalDateTime day = bookingDTO.getStartTime();
        Page<Booking> bookingsDB = bookingService.bookingOfCustomer(uuidCustomer, size, page, day);
        SearchBookingDTO response = bookingMapper.toDTO(bookingsDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
