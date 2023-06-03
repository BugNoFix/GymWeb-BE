package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.controller.dto.booking.BookingDTO;
import com.marcominaudo.gymweb.controller.dto.booking.BookingMapper;
import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.service.BookingService;
import com.marcominaudo.gymweb.utilis.object.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @PostMapping()
    public ResponseEntity<BookingDTO> booking(@RequestBody BookingDTO bookingDTO) throws BookingException, RoomException {
        Booking booking = bookingMapper.DTOToBooking(bookingDTO);
        long roomId = bookingDTO.getRoomId();
        Booking bookingDB = bookingService.newBooking(booking, roomId);
        BookingDTO response = bookingMapper.BookingToDTO(bookingDB);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/pt") // Quali sono i pt in un giorno
    public ResponseEntity<Map<Shift, String>> bookingInfoPt(@RequestBody BookingDTO bookingDTO) {
        LocalDateTime startTime = bookingDTO.getStartTime();
        long roomId = bookingDTO.getRoomId();
        Map<Shift, String> response = bookingService.bookingInfo(roomId, startTime, Role.PT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/customers") // Quali sono i CUSTOMER in un giorno
    public ResponseEntity<Map<Shift, String>> bookingInfoCustomer(@RequestBody BookingDTO bookingDTO) {
        LocalDateTime startTime = bookingDTO.getStartTime();
        long roomId = bookingDTO.getRoomId();
        Map<Shift, String> response = bookingService.bookingInfo(roomId, startTime, Role.CUSTOMER);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
