package com.marcominaudo.gymweb.controller.dto.feedback;

import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FeedbackMapper {

    public FeedbackDTO toDTO(Feedback feedback){
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setText(feedback.getText());
        feedbackDTO.setCreatedTime(feedback.getCreatedTime());
        if(feedback.getUsers() != null) {
            feedbackDTO.setUserFeedbackDTOs(usersToDTO(feedback));
        }
        return feedbackDTO;
    }

    // Convert user in UserFeedbackDTO
    private List<UserFeedbackDTO> usersToDTO(Feedback feedback) {
        List<UserFeedbackDTO> userFeedbackDTOs = new ArrayList<>();
        for (User user : feedback.getUsers()) {
            UserFeedbackDTO userFeedbackDTO = new UserFeedbackDTO(user.getUuid(), user.getName(), user.getSurname(), user.getRole());
            userFeedbackDTOs.add(userFeedbackDTO);
        }
        return userFeedbackDTOs;
    }


    public SearchFeedbackDTO toDTO(Page<Feedback> searchInfo){
        SearchFeedbackDTO searchFeedbackDTO = new SearchFeedbackDTO();
        List<FeedbackDTO> feedbacks = searchInfo.getContent().stream().map(this::toDTO).toList();

        searchFeedbackDTO.setFeedbacks(feedbacks);
        searchFeedbackDTO.setTotalPages(searchInfo.getTotalPages());
        searchFeedbackDTO.setTotalElements(searchInfo.getTotalElements());
        return searchFeedbackDTO;
    }

    public Feedback toFeedback(FeedbackDTO feedbackDTO){
        Feedback feedback = new Feedback();
        feedback.setText(feedbackDTO.getText());
        feedback.setCreatedTime(feedbackDTO.getCreatedTime());
        return feedback;
    }
}
