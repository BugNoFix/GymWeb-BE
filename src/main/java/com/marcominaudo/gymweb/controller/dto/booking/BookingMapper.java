package com.marcominaudo.gymweb.controller.dto.booking;

import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.builder.BookingBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingDTO BookingToDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSubscriptionTime(booking.getSubscriptionTime());
        if(booking.getRoom() != null)
            bookingDTO.setRoomId(booking.getRoom().getId());
        return bookingDTO;
    }

    public Booking DTOToBooking(BookingDTO bookingDTO){
        return new BookingBuilder().builder()
                .startTime(bookingDTO.getStartTime())
                .endTime(bookingDTO.getEndTime())
                .subscriptionTime(bookingDTO.getSubscriptionTime())
                .build();
    }
}
