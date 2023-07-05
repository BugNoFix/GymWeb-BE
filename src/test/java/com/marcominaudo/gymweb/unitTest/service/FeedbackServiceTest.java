package com.marcominaudo.gymweb.unitTest.service;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.FeedbackException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Feedback;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.FeedbackRepository;
import com.marcominaudo.gymweb.service.FeedbackService;
import com.marcominaudo.gymweb.service.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    @Mock
    FeedbackRepository feedbackRepository;

    @Mock
    Utils utils;

    @InjectMocks
    FeedbackService feedbackService;

    UtilsTest utilsTest = new UtilsTest();

    // --------- Save Feedback ----------
    @Test
    // Case: User haven't a pt
    void saveExMissingPt() {
        // Mock
        when(utils.getPtOfCustomer()).thenReturn(null);

        // Test
        FeedbackException thrown = assertThrows(FeedbackException.class, () -> feedbackService.save("Feedback Text"));
        assertEquals(FeedbackException.ExceptionCodes.PT_MISSING.name(), thrown.getMessage());
    }

    @Test
    // Case: Missing body feedback
    void saveExMissingData() {
        // Test
        FeedbackException thrown = assertThrows(FeedbackException.class, () -> feedbackService.save(null));
        assertEquals(FeedbackException.ExceptionCodes.MISSING_DATA.name(), thrown.getMessage());
    }

    @Test
    void saveSuccessful() {
        // Mock
        User pt = utilsTest.getPt("Mario", "rossi", "marioRossiPt@gymweb.com");
        User customer = utilsTest.getCustomer("Marco", "Minaudo", "marco.minaudo@gmail.com", true, pt);
        when(utils.getPtOfCustomer()).thenReturn(pt);
        when(utils.getUser()).thenReturn(customer);
        when(feedbackRepository.save(any(Feedback.class))).then(returnsFirstArg());

        // Test
        Feedback feedback = assertDoesNotThrow(()-> feedbackService.save("Feedback Text"));
        assertEquals("Feedback Text", feedback.getText());
        assertEquals(2, feedback.getUsers().size());
    }
    // ----------------------------------------

    // --------- Get all feedback ----------
    @Test
    // Case: Get feedback of user isn't a pt
    void getsAllFeedbackOfPtExUserNotValid() throws UserException {
        // Mock
        User customer = new User();
        customer.setRole(Role.CUSTOMER);
        when(utils.getUserByUuid(any(String.class))).thenReturn(customer);

        // Test
        FeedbackException thrown = assertThrows(FeedbackException.class, () -> feedbackService.getsAllFeedbackOfPt("uuid Customer", 0 ,5));
        assertEquals(FeedbackException.ExceptionCodes.USER_ROLE_INVALID.name(), thrown.getMessage());
    }

    @Test
        // Case: Get feedback of user isn't a pt
    void getsAllFeedbackOfPtExMissingData() {
        // Test
        FeedbackException thrown = assertThrows(FeedbackException.class, () -> feedbackService.getsAllFeedbackOfPt(null, 0 ,5));
        assertEquals(FeedbackException.ExceptionCodes.MISSING_DATA.name(), thrown.getMessage());
    }

    @Test
    void getsAllFeedbackOfPtSuccessful() throws UserException {
        // Mock
        User pt = new User();
        pt.setRole(Role.PT);
        when(utils.getUserByUuid(any(String.class))).thenReturn(pt);
        when(feedbackRepository.findByUsersId(any(Long.class), any(Pageable.class))).thenReturn(Page.empty());

        // Test
        assertDoesNotThrow(() -> feedbackService.getsAllFeedbackOfPt("uuid Customer", 0 ,5));
    }
    // ----------------------------------------

}
