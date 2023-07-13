package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.controller.dto.bodyDetails.BodyDetailsMapper;
import com.marcominaudo.gymweb.controller.dto.bodyDetails.SearchUserBodyDetailsDTO;
import com.marcominaudo.gymweb.controller.dto.bodyDetails.UserBodyDetailsDTO;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class BodyDetailsMapperTest {


    UtilsTest utilsTest = new UtilsTest();

    BodyDetailsMapper bodyDetailsMapper = new BodyDetailsMapper();

    @Test
    void toDTO(){
        // BodyDetails data
        long id = 1;
        int weight = 80;
        int bodyfat = 20;
        int height = 180;
        int chest = 100;
        int upperArm = 35;
        int shoulders = 100;
        int waist = 89;
        LocalDateTime uploaded = LocalDateTime.now();
        UserBodyDetails userBodyDetails = new UserBodyDetails(id, weight, bodyfat, height, chest, upperArm, shoulders, waist, uploaded, utilsTest.getUser());

        // Mapping to dto
        UserBodyDetailsDTO userBodyDetailsDTO = bodyDetailsMapper.toDTO(userBodyDetails);

        // Test
        assertEquals(weight, userBodyDetailsDTO.getWeight());
        assertEquals(bodyfat, userBodyDetailsDTO.getBodyfat());
        assertEquals(height, userBodyDetailsDTO.getHeight());
        assertEquals(chest, userBodyDetailsDTO.getChest());
        assertEquals(upperArm, userBodyDetailsDTO.getUpperArm());
        assertEquals(shoulders, userBodyDetailsDTO.getShoulders());
        assertEquals(waist, userBodyDetailsDTO.getWaist());
        assertEquals(uploaded, userBodyDetailsDTO.getUploadTime());
    }

    @Test
    void toDTOPage(){
        // Data
        ArrayList<UserBodyDetails> userBodyDetailsList = new ArrayList<>();
        userBodyDetailsList.add(new UserBodyDetails());
        userBodyDetailsList.add(new UserBodyDetails());

        // Mapping to dto
        Page<UserBodyDetails> page = new PageImpl<>(userBodyDetailsList);
        SearchUserBodyDetailsDTO searchUserBodyDetailsDTO = bodyDetailsMapper.toDTO(page);

        // Test
        assertEquals(1 ,searchUserBodyDetailsDTO.getTotalPages());
        assertEquals(2 ,searchUserBodyDetailsDTO.getTotalElements());
    }

    @Test
    void toBodyDetails(){
        // BodyDetails data
        long id = 1;
        int weight = 80;
        int bodyfat = 20;
        int height = 180;
        int chest = 100;
        int upperArm = 35;
        int shoulders = 100;
        int waist = 89;
        LocalDateTime uploaded = LocalDateTime.now();
        UserBodyDetailsDTO userBodyDetailsDTO = new UserBodyDetailsDTO (weight, bodyfat, height, chest, upperArm, shoulders, waist, uploaded);

        // Mapping to dto
        UserBodyDetails userBodyDetails = bodyDetailsMapper.toBodyDetails(userBodyDetailsDTO);

        // Test
        assertEquals(weight, userBodyDetails.getWeight());
        assertEquals(bodyfat, userBodyDetails.getBodyfat());
        assertEquals(height, userBodyDetails.getHeight());
        assertEquals(chest, userBodyDetails.getChest());
        assertEquals(upperArm, userBodyDetails.getUpperArm());
        assertEquals(shoulders, userBodyDetails.getShoulders());
        assertEquals(waist, userBodyDetails.getWaist());
        assertNull(userBodyDetails.getUploadTime());

    }
}
