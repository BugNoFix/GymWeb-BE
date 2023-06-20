package com.marcominaudo.gymweb.controller.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
