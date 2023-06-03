package com.marcominaudo.gymweb.controller.dto.feedback;

import com.marcominaudo.gymweb.controller.dto.room.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFeedbackDTO {

    List<FeedbackDTO> feedbacks;

    int totalPages;

    long totalElements;
}
