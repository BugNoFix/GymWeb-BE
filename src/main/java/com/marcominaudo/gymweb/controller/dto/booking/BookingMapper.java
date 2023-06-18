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

    public BookingDTO bookingToDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setSubscriptionTime(booking.getSubscriptionTime());
        if(booking.getRoom() != null)
            bookingDTO.setRoomId(booking.getRoom().getId());
        return bookingDTO;
    }

    public SearchBookingDTO bookingsToDTO(Page<Booking> searchInfo){
        SearchBookingDTO searchBookingDTO = new SearchBookingDTO();
        List<BookingDTO> bookings = searchInfo.getContent().stream().map(this::bookingToDTO).toList();

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

    public List<BookingDTO> mapToDTO(Map<Shift, List<String>> map){
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        map.keySet().stream().forEach(key ->{
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setStartTime(key.getStartShift());
            bookingDTO.setEndTime(key.getEndShift());
            bookingDTO.setName(map.get(key).get(0));
            bookingDTO.setSurname(map.get(key).get(1));
            bookingDTOs.add(bookingDTO);
        });
       return bookingDTOs;
    }
}
