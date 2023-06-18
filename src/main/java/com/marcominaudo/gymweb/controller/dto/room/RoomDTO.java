package com.marcominaudo.gymweb.controller.dto.room;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private String name;

    private boolean isActive;

    private int size;

    private long id;
}
