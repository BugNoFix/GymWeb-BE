package com.marcominaudo.gymweb.service.bookingSearch;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@NoArgsConstructor
public class BookingStrategyFactory {

    private Map<BookingSearchType, BookingSearchStrategy> bookingStrategies;

    // Create a map of strategies
    @Autowired
    public BookingStrategyFactory(Set<BookingSearchStrategy> strategySet){
        bookingStrategies = new HashMap<>();
        strategySet.forEach(s -> bookingStrategies.put(s.getTypeSearch(), s));
    }
    public BookingSearchStrategy getStrategy(BookingSearchType bookingSearchType){
        return bookingStrategies.get(bookingSearchType);
    }

}
