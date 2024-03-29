package com.marcominaudo.gymweb.service.bookingSearch;

import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.repository.BookingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
@AllArgsConstructor
@NoArgsConstructor
public class BookingSearchPt implements BookingSearchStrategy {
    @Autowired
    BookingRepository bookingRepository;
    @Override
    public List<Booking> search(long roomId, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        List<Booking> bookings = bookingRepository.findPtByRoomIdAndDay(roomId, startDatetime, endDatetime);
        return bookings;
    }

    @Override
    public BookingSearchType getTypeSearch() {
       return BookingSearchType.PT;
    }
}
