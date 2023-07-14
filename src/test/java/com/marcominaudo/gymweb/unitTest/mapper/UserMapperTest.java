package com.marcominaudo.gymweb.unitTest.mapper;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.controller.dto.user.SearchUserDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserDTO;
import com.marcominaudo.gymweb.controller.dto.user.UserMapper;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    UtilsTest utilsTest = new UtilsTest();

    UserMapper userMapper = new UserMapper();

    // Case User with pt
    @Test
    void toDTO(){
        // User data
        long id = 1;
        String uuid = UUID.randomUUID().toString();
        String email = "marco@gmail.com";
        String name = "marco";
        String surname = "minaudo";
        String password = "pass";
        LocalDate subscriptionStart = LocalDate.of(2022, 06,01);
        LocalDate subscriptionEnd = LocalDate.of(2022, 07,01);
        LocalDateTime created = LocalDateTime.of(2021, 01, 01, 0, 0);
        boolean privacy = true;
        User pt = utilsTest.getPt("giovanni", "rossi", "gioRoPt@gmweb.com");
        boolean isActive = true;

        // Create user and convert to DTO
        User user = new User(id, uuid, email, name, surname, password, subscriptionStart, subscriptionEnd, created, privacy, Role.CUSTOMER, pt, isActive);
        UserDTO userDTO = userMapper.toDTO(user);

        // Test
        assertEquals(email, userDTO.getEmail());
        assertEquals(name, userDTO.getName());
        assertEquals(surname, userDTO.getSurname());
        assertEquals(null, userDTO.getPassword());
        assertEquals(subscriptionStart, userDTO.getSubscriptionStart());
        assertEquals(subscriptionEnd, userDTO.getSubscriptionEnd());
        assertEquals(privacy, userDTO.isPrivacy());
        assertEquals(pt.getUuid(), userDTO.getUuidPt());
    }

    // Case User without pt
    @Test
    void toDTOWithoutPt(){
        // User data
        long id = 1;
        String uuid = UUID.randomUUID().toString();
        String email = "marco@gmail.com";
        String name = "marco";
        String surname = "minaudo";
        String password = "pass";
        LocalDate subscriptionStart = LocalDate.of(2022, 06,01);
        LocalDate subscriptionEnd = LocalDate.of(2022, 07,01);
        LocalDateTime created = LocalDateTime.of(2021, 01, 01, 0, 0);
        boolean privacy = true;
        boolean isActive = true;

        // Create user and convert to DTO
        User user = new User(id, uuid, email, name, surname, password, subscriptionStart, subscriptionEnd, created, privacy, Role.CUSTOMER, null, isActive);
        UserDTO userDTO = userMapper.toDTO(user);

        // Test
        assertEquals(email, userDTO.getEmail());
        assertEquals(name, userDTO.getName());
        assertEquals(surname, userDTO.getSurname());
        assertNull(userDTO.getPassword());
        assertEquals(subscriptionStart, userDTO.getSubscriptionStart());
        assertEquals(subscriptionEnd, userDTO.getSubscriptionEnd());
        assertEquals(privacy, userDTO.isPrivacy());
        assertNull(userDTO.getUuidPt());
        assertEquals(Role.CUSTOMER, userDTO.getRole());
    }

    @Test
    // Case: toDTO(Page<User>)
    void toDTOPage(){
        // Data
        ArrayList<User> users = new ArrayList<>();
        users.add(utilsTest.getUser());
        users.add(utilsTest.getUser());

        // Convert to DTO
        Page<User> page = new PageImpl<>(users);
        SearchUserDTO searchUserDTO = userMapper.toDTO(page);

        // Test
        assertEquals(1 ,searchUserDTO.getTotalPages());
        assertEquals(2 ,searchUserDTO.getTotalElements());
        assertEquals(2 ,searchUserDTO.getUsers().size());
    }

    @Test
    void toUser(){
        // User data
        String uuid = UUID.randomUUID().toString();
        String uuidPt = UUID.randomUUID().toString();
        String email = "marco@gmail.com";
        String name = "marco";
        String surname = "minaudo";
        String password = "pass";
        LocalDate subscriptionStart = LocalDate.of(2022, 06,01);
        LocalDate subscriptionEnd = LocalDate.of(2022, 07,01);
        LocalDateTime created = LocalDateTime.of(2021, 01, 01, 0, 0);
        boolean privacy = true;
        boolean isActive = true;

        // Create dto and convert to user
        UserDTO userDTO = new UserDTO(name, surname, email, subscriptionStart, subscriptionEnd, Role.CUSTOMER, privacy,  isActive, uuidPt, password, uuid);
        User user = userMapper.toUser(userDTO);

        // Test
        assertEquals(email, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(surname, user.getSurname());
        assertEquals(password, user.getPassword());
        assertEquals(subscriptionStart, user.getSubscriptionStart());
        assertEquals(subscriptionEnd, user.getSubscriptionEnd());
        assertEquals(privacy, user.getPrivacy());
        assertEquals(Role.CUSTOMER, user.getRole());
        assertEquals(isActive, user.getIsActive());
        assertEquals(null, user.getUuid());
    }
}
