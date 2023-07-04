package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackDTO;
import com.marcominaudo.gymweb.controller.dto.feedback.FeedbackMapper;
import com.marcominaudo.gymweb.controller.dto.feedback.SearchFeedbackDTO;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FeedbackMapperTest {

    UtilsTest utilsTest = new UtilsTest();

    FeedbackMapper feedbackMapper = new FeedbackMapper();

    @Test
    void toDTO(){
        // Feedback data
        long id = 1;
        String text = "text";
        LocalDateTime createdTime = LocalDateTime.now();
        User user1 = utilsTest.getUser();
        List<User> users = Arrays.asList(user1, new User());

        //Mapping to dto
        Feedback feedback = new Feedback(id, text, createdTime, users);
        FeedbackDTO feedbackDTO = feedbackMapper.toDTO(feedback);

        // Test
        assertEquals(text, feedbackDTO.getText());
        assertEquals(createdTime, feedbackDTO.getCreatedTime());
        assertEquals(users.size(), feedbackDTO.getUserFeedbackDTOs().size());
        assertEquals(user1.getName(), feedbackDTO.getUserFeedbackDTOs().get(0).getName());
        assertEquals(user1.getSurname(), feedbackDTO.getUserFeedbackDTOs().get(0).getSurname());
        assertEquals(user1.getUuid(), feedbackDTO.getUserFeedbackDTOs().get(0).getUserUuid());
        assertEquals(user1.getRole(), feedbackDTO.getUserFeedbackDTOs().get(0).getRole());
    }

    @Test
    void toDTOPage(){
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        feedbacks.add(new Feedback());
        feedbacks.add(new Feedback());

        Page<Feedback> page = new PageImpl<>(feedbacks);
        SearchFeedbackDTO searchFeedbackDTO = feedbackMapper.toDTO(page);

        assertEquals(1, searchFeedbackDTO.getTotalPages());
        assertEquals(2, searchFeedbackDTO.getTotalElements());
    }
}
