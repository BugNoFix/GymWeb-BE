package com.marcominaudo.gymweb.controller.dto.booking;

import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.builder.BookingBuilder;
import com.marcominaudo.gymweb.utilis.object.Shift;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BookingMapper {

    public BookingDTO toDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSubscriptionTime(booking.getSubscriptionTime());
        bookingDTO.setSurname(booking.getUser().getSurname());
        bookingDTO.setName(booking.getUser().getName());
        if(booking.getRoom() != null)
            bookingDTO.setRoomId(booking.getRoom().getId());
        return bookingDTO;
    }

    public SearchBookingDTO toDTO(Page<Booking> searchInfo){
        SearchBookingDTO searchBookingDTO = new SearchBookingDTO();
        List<BookingDTO> bookings = searchInfo.getContent().stream().map(this::toDTO).toList(); // Convert each booking in bookingDTO

        searchBookingDTO.setBookings(bookings);
        searchBookingDTO.setTotalPages(searchInfo.getTotalPages());
        searchBookingDTO.setTotalElements(searchInfo.getTotalElements());
        return searchBookingDTO;

    }

    public Booking toBooking(BookingDTO bookingDTO){
        return new BookingBuilder().builder()
            .startTime(bookingDTO.getStartTime())
            .endTime(bookingDTO.getEndTime())
            .subscriptionTime(bookingDTO.getSubscriptionTime())
            .build();
    }
}
