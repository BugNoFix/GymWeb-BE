package com.marcominaudo.gymweb.controller.dto.feedback;

import com.marcominaudo.gymweb.controller.dto.booking.SearchBookingDTO;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedbackMapper {

    public FeedbackDTO feedbackToDTO(Feedback feedback){
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setText(feedback.getText());
        feedbackDTO.setCreatedTime(feedback.getCreatedTime());
        if(feedback.getUser() != null) {
            List<UserFeedbackDTO> userFeedbackDTOs = new ArrayList<>();
            for (User user : feedback.getUser()) {
                UserFeedbackDTO userFeedbackDTO = new UserFeedbackDTO(user.getUuid(), user.getName(), user.getSurname(), user.getRole());
                userFeedbackDTOs.add(userFeedbackDTO);
            }
            feedbackDTO.setUserFeedbackDTOs(userFeedbackDTOs);
        }
        return feedbackDTO;
    }


    public SearchFeedbackDTO feedbacksToDTO(Page<Feedback> searchInfo){
        SearchFeedbackDTO searchFeedbackDTO = new SearchFeedbackDTO();
        List<FeedbackDTO> feedbacks = searchInfo.getContent().stream().map(this::feedbackToDTO).toList();

        searchFeedbackDTO.setFeedbacks(feedbacks);
        searchFeedbackDTO.setTotalPages(searchInfo.getTotalPages());
        searchFeedbackDTO.setTotalElements(searchInfo.getTotalElements());
        return searchFeedbackDTO;
    }

    public Feedback DTOTofeedback(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        feedback.setText(feedbackDTO.getText());
        feedback.setCreatedTime(feedbackDTO.getCreatedTime());
        return feedback;
    }
}
