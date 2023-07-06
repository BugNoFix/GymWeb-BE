package com.marcominaudo.gymweb.integration.repository;

import com.marcominaudo.gymweb.UtilsTest;
import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.repository.BookingRepository;
import com.marcominaudo.gymweb.repository.RoomRepository;
import com.marcominaudo.gymweb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class bookingRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilsTest utilsTest;

    @BeforeEach
    void init(){
        bookingRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    //TODO: Rifare il test
    void findAllBetweenBookingDate(){
        // Add data to h2 db
        User user = userRepository.save(utilsTest.getUser());
        Room room = new Room(1, "Generic room", true, 5);
        room = roomRepository.save(room);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(utilsTest.getBooking(1, 0, 30, user, room));
        bookingList.add(utilsTest.getBooking(2, -30, 30, user, room));
        bookingList.add(utilsTest.getBooking(3, -15, 30, user, room));
        bookingList.add(utilsTest.getBooking(4, 0, 45, user, room));
        bookingRepository.saveAll(bookingList);


        //Test
        LocalDateTime today = utilsTest.getToday();
        List<Booking> result = bookingRepository.findAllBetweenBookingDate(today.plusMinutes(-15), today.plusMinutes(30), room.getId());
        assertEquals(2, result.size());
    }

    @Test
    void findCustomerByRoomIdAndDay(){
        // Add data to h2 db
        User customer = userRepository.save(utilsTest.getUser());
        User pt = userRepository.save(utilsTest.getPt("Marco", "Minaudo", "marco@gmail.com"));
        Room room = new Room(1, "Generic room", true, 5);
        room = roomRepository.save(room);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(utilsTest.getBooking(1, 0, 30, customer, room));
        bookingList.add(utilsTest.getBooking(2, -30, 0, customer, room));
        bookingList.add(utilsTest.getBooking(3, 0, 30, pt, room));
        bookingList.add(utilsTest.getBooking(4, -15, 0, pt, room));
        bookingRepository.saveAll(bookingList);

        //Test
        LocalDateTime today = utilsTest.getToday();
        List<Booking> result = bookingRepository.findCustomerByRoomIdAndDay(room.getId(), today);
        assertEquals(2, result.size());
    }

    @Test
    void findPtByRoomIdAndDay(){
        // Add data to h2 db
        User customer = userRepository.save(utilsTest.getUser());
        User pt = userRepository.save(utilsTest.getPt("Marco", "Minaudo", "marco@gmail.com"));
        Room room = new Room(1, "Generic room", true, 5);
        room = roomRepository.save(room);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(utilsTest.getBooking(1, 0, 30, customer, room));
        bookingList.add(utilsTest.getBooking(2, -30, 0, customer, room));
        bookingList.add(utilsTest.getBooking(3, 0, 30, pt, room));
        bookingList.add(utilsTest.getBooking(4, -15, 0, pt, room));
        bookingRepository.saveAll(bookingList);

        //Test
        LocalDateTime today = utilsTest.getToday();
        List<Booking> result = bookingRepository.findPtByRoomIdAndDay(room.getId(), today);
        assertEquals(2, result.size());
    }
    @Test
    void findAllBookingOfUserInOneDay(){
        // Add data to h2 db
        User user = userRepository.save(utilsTest.getUser());
        Room room = new Room(1, "Generic room", true, 5);
        room = roomRepository.save(room);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(utilsTest.getBooking(1, 0, 30, user, room));
        bookingList.add(utilsTest.getBooking(2, -30, 0, user, room));
        bookingRepository.saveAll(bookingList);

        //Test
        LocalDateTime today = utilsTest.getToday();
        Page<Booking> result = bookingRepository.findAllBookingOfUserInOneDay(user.getId(), Pageable.unpaged(), today);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void findAllByUserIdAndBetweenBookingDate(){
        // Add data to h2 db
        User user1 = userRepository.save(utilsTest.getUser("marco1@gmail.com", Role.CUSTOMER, true));
        User user2 = userRepository.save(utilsTest.getUser("marco2@gmail.com", Role.CUSTOMER, true));
        Room room = new Room(1, "Generic room", true, 5);
        room = roomRepository.save(room);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(utilsTest.getBooking(1, 0, 30, user1, room));
        bookingList.add(utilsTest.getBooking(2, -30, 0, user1, room));
        bookingRepository.saveAll(bookingList);

        //Test
        LocalDateTime today = utilsTest.getToday();
        long result = bookingRepository.findAllByUserIdAndBetweenBookingDate(user1.getId(), today.plusMinutes(-15), today.plusMinutes(30));
        assertEquals(2, result);
    }
}
