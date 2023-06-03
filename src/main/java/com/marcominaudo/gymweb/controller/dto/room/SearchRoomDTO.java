package com.marcominaudo.gymweb.controller.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRoomDTO {
    List<RoomDTO> rooms;

    int totalPages;

    long totalElements;
}
