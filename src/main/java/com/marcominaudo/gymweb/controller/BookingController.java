package com.marcominaudo.gymweb.controller;

import com.marcominaudo.gymweb.exception.exceptions.BookingException;
import com.marcominaudo.gymweb.exception.exceptions.RoomException;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.service.BookingService;
import com.marcominaudo.gymweb.utilis.object.WorkShift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/{roomId}")
    public ResponseEntity<Booking> booking(@RequestBody Booking booking, @PathVariable("roomId") long roomId) throws BookingException, RoomException {
        Booking response = bookingService.newBooking(booking, roomId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{roomId}/pt") // Quali sono i pt in un giorno
    public ResponseEntity<Map<WorkShift, String>> bookingInfoPt(@RequestBody Booking booking, @PathVariable("roomId") long roomId) {
        Map<WorkShift, String> response = bookingService.bookingInfoPt(roomId, booking.getStartTime()); // TODO: uso il booking solo per la data, usare dto
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
