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
public class BookingSearchCustomer implements BookingSearchStrategy {
    @Autowired
    BookingRepository bookingRepository;

    @Override
    public BookingSearchType getTypeSearch() {
        return BookingSearchType.CUSTOMER;
    }

    @Override
    public List<Booking> search(long roomId, LocalDateTime day) {
        List<Booking> bookings = bookingRepository.findCustomerByRoomIdAndDay(roomId, day);
        bookings.forEach(b -> b.setSubscriptionTime(null));
        return bookings;
    }
}
