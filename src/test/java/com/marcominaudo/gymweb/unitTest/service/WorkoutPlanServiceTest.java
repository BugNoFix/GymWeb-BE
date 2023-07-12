package com.marcominaudo.gymweb.unitTest.service;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.repository.WorkoutPlanRepository;
import com.marcominaudo.gymweb.service.FilesStorageService;
import com.marcominaudo.gymweb.service.Utils;
import com.marcominaudo.gymweb.service.WorkoutPlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.marcominaudo.gymweb.exception.exceptions.UserException.ExceptionCodes.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutPlanServiceTest {

    @Mock
    WorkoutPlanRepository workoutPlanRepository;

    @Mock
    FilesStorageService filesStorageService;

    @Mock
    Utils utils;

    @InjectMocks
    WorkoutPlanService workoutPlanService;


    UtilsTest utilsTest = new UtilsTest();

    @Test
    void getWorkoutPlan() {
        // Mock
        WorkoutPlan workoutPlanDB = utilsTest.getWorkoutPlans().get(0);
        when(workoutPlanRepository.findFirstByUserIdOrderByUploadTime(any(Long.class))).thenReturn(workoutPlanDB);
        when(utils.getLoggedUser()).thenReturn(utilsTest.getUser());

        // Test
        WorkoutPlan workoutPlan = assertDoesNotThrow(()-> workoutPlanService.getWorkoutPlan());
        assertEquals(workoutPlan, workoutPlanDB);
    }

    @Test
    void getWorkoutPlans() {
        // Mock
        List<WorkoutPlan> workoutPlansDB = utilsTest.getWorkoutPlans();
        when(workoutPlanRepository.findByUserIdOrderByUploadTime(any(Long.class), any(Pageable.class))).thenReturn(new PageImpl<>(workoutPlansDB));
        when(utils.getLoggedUser()).thenReturn(utilsTest.getUser());

        // Test
        Page<WorkoutPlan> workoutPlans = assertDoesNotThrow(()-> workoutPlanService.getWorkoutPlans(0, 5));
        assertEquals(2, workoutPlans.getTotalElements());
        assertEquals(workoutPlans.getContent(), workoutPlansDB);
    }

    // --------- Save file ----------
    @Test
    void saveFileThrowUserException() throws UserException {
        // Mock
        when(utils.getUserByUuid(anyString())).thenThrow(new UserException(USER_NOT_FOUND));

        // Test
        UserException thrown = assertThrows(UserException.class, ()-> workoutPlanService.saveFile(null, "uuid"));
        assertEquals(USER_NOT_FOUND.name(), thrown.getMessage());
    }

    @Test
    void saveFileSuccessful() throws UserException {
        // Mock
        when(utils.getLoggedUser()).thenReturn(utilsTest.getUser());
        when(filesStorageService.save(any(), any(String.class))).thenReturn("upload/");
        when(utils.getUserByUuid(anyString())).thenReturn(utilsTest.getUser());
        when(workoutPlanRepository.save(any())).then(returnsFirstArg());

        // Test
        WorkoutPlan workoutPlan = assertDoesNotThrow(()-> workoutPlanService.saveFile(null, "uuid"));
        assertEquals("upload/", workoutPlan.getPath());
    }
    // ----------------------------------------

    // --------- Get WorkoutPlan of customer ----------
    @Test
    void getWorkoutPlansOfCustomerThrowUserException() throws UserException {
        // Mock
        when(utils.getUserByUuid(anyString())).thenThrow(new UserException(USER_NOT_FOUND));

        // Test
        UserException thrown = assertThrows(UserException.class, ()-> workoutPlanService.getWorkoutPlansOfCustomer(0, 5, "uuidCustomer"));
        assertEquals(USER_NOT_FOUND.name(), thrown.getMessage());
    }

    @Test
    void getWorkoutPlansOfCustomerSuccessful() throws UserException {
        // Mock
        when(utils.getUserByUuid(anyString())).thenReturn(utilsTest.getUser());
        when(workoutPlanRepository.findByUserIdOrderByUploadTime(any(Long.class), any(Pageable.class))).thenReturn(new PageImpl<>(utilsTest.getWorkoutPlans()));

        // Test
        Page<WorkoutPlan> workoutPlan = assertDoesNotThrow(()-> workoutPlanService.getWorkoutPlansOfCustomer(0, 5, "uuidCustomer"));
        assertEquals(2, workoutPlan.getTotalElements());
    }
    // ----------------------------------------

}
