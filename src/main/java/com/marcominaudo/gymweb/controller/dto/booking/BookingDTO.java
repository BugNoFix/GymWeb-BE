package com.marcominaudo.gymweb.controller.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Creo solamente 1 booking DTO perche la differenza tra il request e la response non c'è
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private LocalDateTime subscriptionTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    long roomId;

    private String name;
    private String surname;
}
