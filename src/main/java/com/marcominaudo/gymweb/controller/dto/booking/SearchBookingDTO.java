package com.marcominaudo.gymweb.controller.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookingDTO {
    List<BookingDTO> bookings;

    int totalPages;

    long totalElements;
}
