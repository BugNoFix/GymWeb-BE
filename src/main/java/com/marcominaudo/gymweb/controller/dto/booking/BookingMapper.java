package com.marcominaudo.gymweb.controller.dto.booking;

import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.builder.BookingBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingMapper {

    public BookingDTO bookingToDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSubscriptionTime(booking.getSubscriptionTime());
        if(booking.getRoom() != null)
            bookingDTO.setRoomId(booking.getRoom().getId());
        return bookingDTO;
    }

    public SearchBookingDTO BookingsToDTO(Page<BookingDTO> searchInfo){
        SearchBookingDTO searchBookingDTO = new SearchBookingDTO();
        List<Booking> bookings = searchInfo.getContent().stream().map(this::DTOToBooking).toList();

        searchBookingDTO.setBookings(searchBookingDTO.getBookings());
        searchBookingDTO.setTotalPages(searchInfo.getTotalPages());
        searchBookingDTO.setTotalElements(searchInfo.getTotalElements());
        return searchBookingDTO;

    }

        public Booking DTOToBooking(BookingDTO bookingDTO){
        return new BookingBuilder().builder()
                .startTime(bookingDTO.getStartTime())
                .endTime(bookingDTO.getEndTime())
                .subscriptionTime(bookingDTO.getSubscriptionTime())
                .build();
    }
}
