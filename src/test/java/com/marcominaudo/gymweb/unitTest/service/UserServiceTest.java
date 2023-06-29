package com.marcominaudo.gymweb.unitTest.service;


import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.exception.exceptions.BodyDetailsException;
import com.marcominaudo.gymweb.exception.exceptions.InvalidRegisterException;
import com.marcominaudo.gymweb.exception.exceptions.UserException;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.UserBodyDetailsRepository;
import com.marcominaudo.gymweb.repository.UserRepository;
import com.marcominaudo.gymweb.service.UserService;
import com.marcominaudo.gymweb.service.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    Utils utils;

    @Mock
    UserRepository userRepository;

    @Mock
    UserBodyDetailsRepository userBodyDetailsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    UtilsTest utilsTest = new UtilsTest();

    // --------- Register ----------
    @Test
    void registerExMissingRequiredValue() {
        // Test
        User user = utilsTest.getUser(null, Role.CUSTOMER, true);
        InvalidRegisterException thrown = assertThrows(InvalidRegisterException.class, () -> userService.register(user));
        assertEquals(InvalidRegisterException.ExceptionCodes.MISSING_REQUIRED_VALUE.name(), thrown.getMessage());
    }

    @Test
    void registerExEmailInvalid() {
        // Test
        User user = utilsTest.getUser("marco@", Role.CUSTOMER, true);
        InvalidRegisterException thrown = assertThrows(InvalidRegisterException.class, () -> userService.register(user));
        assertEquals(InvalidRegisterException.ExceptionCodes.EMAIL_INVALID.name(), thrown.getMessage());
    }

    @Test
    void registerExUserAlreadyExist() {
        // Mock
        User userMock = utilsTest.getUser("marco@gmail.com", Role.CUSTOMER, true);
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(userMock));

        // Test
        User user = utilsTest.getUser("marco2@gmail.com", Role.CUSTOMER, true);
        InvalidRegisterException thrown = assertThrows(InvalidRegisterException.class, () -> userService.register(user));
        assertEquals(InvalidRegisterException.ExceptionCodes.USER_ALREADY_EXIST.name(), thrown.getMessage());
    }

    @Test
    void registerSuccessful() {
        // Mock
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(String.class))).thenReturn("Password");
        when(userRepository.save(any(User.class))).then(returnsFirstArg());


        // Test
        User user = utilsTest.getUser("marco2@gmail.com", Role.CUSTOMER, true);
        assertAll(() -> userService.register(user));
    }
    // --------------------------------

    // --------- getBodyDetailsOfCustomer ----------
    @Test
    void getBodyDetailsOfCustomerExPrivacyNotEnabled() throws UserException {
        // Mock
        User user = utilsTest.getUser("marco@gmail.com", Role.CUSTOMER, false);
        when(utils.getUserByUuid(any(String.class))).thenReturn(user);

        // Test
        BodyDetailsException thrown = assertThrows(BodyDetailsException.class, () -> userService.getBodyDetailsOfCustomer(0, 5, "uuid"));
        assertEquals(BodyDetailsException.ExceptionCodes.PRIVACY_NOT_ENABLED.name(), thrown.getMessage());
    }

    @Test
    void getBodyDetailsOfCustomerSuccessful() throws UserException {
        // Mock
        User user = utilsTest.getUser("marco@gmail.com", Role.CUSTOMER, true);
        when(utils.getUserByUuid(any(String.class))).thenReturn(user);
        when(userBodyDetailsRepository.findByUserId(any(Long.class), any(Pageable.class))).thenReturn(Page.empty());

        // Test
        assertAll(() -> userService.getBodyDetailsOfCustomer(0, 5, "uuid"));
    }
    // --------------------------------

    // --------- Update User ----------
    @Test
    void updateUseIsActive() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setIsActive(false);
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change isActive field
        User userChanges =  new User();
        userChanges.setIsActive(true);
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(true, userChanges.getIsActive());
    }

    @Test
    void updateUserPrivacy() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setPrivacy(false);
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change privacy field
        User userChanges =  new User();
        userChanges.setPrivacy(true);
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(true, userUpdated.getPrivacy());
    }

    @Test
    void updateUserRole() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setRole(Role.CUSTOMER);
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change role field
        User userChanges =  new User();
        userChanges.setRole(Role.PT);
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(Role.PT, userUpdated.getRole());
    }

    @Test
    void updateUserName() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setName("Francesco");
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change name field
        User userChanges =  new User();
        userChanges.setName("Marco");
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals("Marco", userUpdated.getName());
    }

    @Test
    void updateUserSurname() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setSurname("Milicia");
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change surname field
        User userChanges =  new User();
        userChanges.setSurname("Minaudo");
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals("Minaudo", userUpdated.getSurname());
    }

    @Test
    void updateUserEmail() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setEmail("Marco@gmail.cm");
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change email field
        User userChanges =  new User();
        userChanges.setEmail("Marco@gymweb.com");
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals("Marco@gymweb.com", userUpdated.getEmail());
    }

    @Test
    void updateUserPt() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setEmail("Marco@gmail.cm");
        User pt = utilsTest.getPt("Giovanni", "Peluso", "GiovanniPt@gymweb.com");
        when(utils.getUserByUuid("uuid")).thenReturn(userBeforeUpdate);
        when(utils.getUserByUuid("uuidPt")).thenReturn(pt);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        // Change pt of user
        User userChanges =  new User();
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(pt, userUpdated.getPt());
    }

    @Test
    void updateUserPassword() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setPassword("oldPassword");
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("newPassword");


        // Change password
        User userChanges =  new User();
        userChanges.setPassword("newPassword");
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals("newPassword", userUpdated.getPassword());
    }

    @Test
    void updateSubscriptionStart() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setSubscriptionStart(LocalDate.now());
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());


        // Change SubscriptionStart
        User userChanges =  new User();
        userChanges.setSubscriptionStart(LocalDate.now().plusMonths(-1));
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(LocalDate.now().plusMonths(-1), userUpdated.getSubscriptionStart());
    }

    @Test
    void updateSubscriptionEnd() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setSubscriptionEnd(LocalDate.now());
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());


        // Change SubscriptionStart
        User userChanges =  new User();
        userChanges.setSubscriptionEnd(LocalDate.now().plusMonths(-1));
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(LocalDate.now().plusMonths(-1), userUpdated.getSubscriptionEnd());
    }

    @Test
    void updateWithoutChanges() throws UserException {
        // Mock
        User userBeforeUpdate = utilsTest.getUser();
        userBeforeUpdate.setSubscriptionEnd(LocalDate.now());
        when(utils.getUserByUuid(any(String.class))).thenReturn(userBeforeUpdate);
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        
        // No changes
        User userChanges =  new User();
        User userUpdated = userService.updateUser(userChanges, "uuid", "uuidPt");

        // Test
        assertEquals(userUpdated, userBeforeUpdate);
    }

    // --------------------------------

}
