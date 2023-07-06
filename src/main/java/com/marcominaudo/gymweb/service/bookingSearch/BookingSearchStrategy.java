package com.marcominaudo.gymweb.service.bookingSearch;

import com.marcominaudo.gymweb.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingSearchStrategy {

    List<Booking> search(long roomId, LocalDateTime day);

    BookingSearchType getTypeSearch();
}
