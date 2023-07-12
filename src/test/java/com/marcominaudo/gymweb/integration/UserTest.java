package com.marcominaudo.gymweb.integration;


import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterException;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserRepository;
import com.marcominaudo.gymweb.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)

public class UserTest {

    @Autowired
    UserService userService;

    @Autowired
    UtilsTest utilsTest;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init(){
        userRepository.findAll();
        userRepository.deleteAll();

        User user = utilsTest.getUser("admin@gmail.com", Role.ADMIN, true);
        userRepository.save(user);

        user = utilsTest.getUser("pt@gmail.com", Role.PT, true);
        userRepository.save(user);

        user = utilsTest.getUser("customer@gmail.com", Role.CUSTOMER, true);
        userRepository.save(user);
    }

    @Test
    void registerThrowMissingRequiredValue(){
        // Missing surname
        User user = utilsTest.getUser();
        user.setSurname("");
        InvalidRegisterException thrown = assertThrows(InvalidRegisterException.class,() -> userService.register(user));
        assertEquals(InvalidRegisterException.ExceptionCodes.MISSING_REQUIRED_VALUE.name(), thrown.getMessage());

        // Missing name
        User user2 = utilsTest.getUser();
        user2.setName("");
        thrown = assertThrows(InvalidRegisterException.class,() -> userService.register(user2));
        assertEquals(InvalidRegisterException.ExceptionCodes.MISSING_REQUIRED_VALUE.name(), thrown.getMessage());

        // Missing Role
        User user3 = utilsTest.getUser();
        user3.setRole(null);
        thrown = assertThrows(InvalidRegisterException.class,() -> userService.register(user3));
        assertEquals(InvalidRegisterException.ExceptionCodes.MISSING_REQUIRED_VALUE.name(), thrown.getMessage());
    }

    @Test
    void registerSuccess(){
        User user = utilsTest.getUser();
        assertAll(() -> userService.register(user));
    }

    @Test
    void updateUser(){
        User user = userRepository.findByEmail("pt@gmail.com").get();
        user.setEmail("newEmail@gmail.com");
        assertAll(() -> userService.updateUser(user, user.getUuid(), null));

        Optional<User> actual = userRepository.findByEmail("newEmail@gmail.com");
        assertAll(() -> actual.orElseThrow());
    }
}
