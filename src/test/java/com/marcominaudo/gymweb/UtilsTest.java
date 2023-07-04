package com.marcominaudo.gymweb;

import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.WorkoutPlan;
import com.marcominaudo.gymweb.model.builder.BookingBuilder;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import com.marcominaudo.gymweb.model.builder.WorkoutPlanBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UtilsTest {

    LocalDateTime today = LocalDateTime.of(2022, 12, 12, 12, 30);

    public Room getRoom(String name, boolean isActive){
        return new Room(1,name, isActive, 3);
    }

    public Room getRoom(String name, boolean isActive, int size){
        return new Room(1,name, isActive, size);
    }

    public User getUser(){
        return new UserBuilder()
            .uuid(UUID.randomUUID().toString())
            .id(1)
            .name("Marco")
            .surname("Minaudo")
            .subscriptionStart(today.toLocalDate())
            .subscriptionEnd(today.toLocalDate().plusYears(1))
            .password("12345")
            .privacy(true)
            .role(Role.CUSTOMER)
            .email("marco.minaudo@gmail.com")
            .isActive(true)
            .created(LocalDateTime.now())
            .build();
    }

    public User getUser(String email, Role role, boolean privacy){
        return new UserBuilder()
                .uuid(UUID.randomUUID().toString())
                .name("Marco")
                .surname("Minaudo")
                .subscriptionStart(today.toLocalDate())
                .subscriptionEnd(today.toLocalDate().plusYears(1))
                .password("12345")
                .privacy(privacy)
                .role(role)
                .email(email)
                .isActive(true)
                .created(LocalDateTime.now())
                .build();
    }

    public User getCustomer(String name, String Surname, String email, boolean privacy, User pt){
        return new UserBuilder()
                .uuid(UUID.randomUUID().toString())
                .name(name)
                .surname(Surname)
                .subscriptionStart(today.toLocalDate())
                .subscriptionEnd(today.toLocalDate().plusYears(1))
                .password("12345")
                .privacy(privacy)
                .role(Role.CUSTOMER)
                .email(email)
                .isActive(true)
                .created(LocalDateTime.now())
                .pt(pt)
                .build();
    }

    public User getPt(String name, String Surname, String email){
        return new UserBuilder()
                .uuid(UUID.randomUUID().toString())
                .name(name)
                .surname(Surname)
                .subscriptionStart(today.toLocalDate())
                .subscriptionEnd(today.toLocalDate().plusYears(1))
                .password("12345")
                .role(Role.PT)
                .email(email)
                .created(LocalDateTime.now())
                .build();
    }

    public Booking getBooking(int addStartMinutes, int addEndMinutes, User user){
        return new BookingBuilder()
            .user(user)
            .startTime(today.plusMinutes(addStartMinutes))
            .endTime(today.plusMinutes(addEndMinutes))
            .subscriptionTime(today)
            .id(1)
            .build();
    }
    public Booking getBooking(long id, int addStartMinutes, int addEndMinutes, User user, Room room){
        Booking booking = getBooking(addStartMinutes, addEndMinutes, user);
        booking.setRoom(room);
        booking.setId(id);
        return booking;
    }
    public Booking getBooking(LocalDateTime start, LocalDateTime end, User user){
        return new BookingBuilder()
                .user(user)
                .startTime(start)
                .endTime(end)
                .subscriptionTime(today)
                .id(1)
                .build();
    }

    public List<Booking> get2Booking(){
        List<Booking> bookings = new ArrayList<>();
        bookings.add(getBooking(0, 30, getUser()));
        bookings.add(getBooking(-30, 30, getUser()));
        return bookings;
    }

    public List<Booking> get3Booking(){
        List<Booking> bookings = new ArrayList<>();
        bookings.add(getBooking(0, 30, getUser()));
        bookings.add(getBooking(-30, 30, getUser()));
        bookings.add(getBooking(-15, 30, getUser()));
        return bookings;
    }

    public List<WorkoutPlan> getWorkoutPlans(){
        List<WorkoutPlan> workoutPlans = new ArrayList<>();
        User pt = getPt("giovanni", "suriano", "giovaPt@gymweb.com");
        User customer = getCustomer("Marco", "Minaudo", "Marco@gmailcom", true, pt);
        WorkoutPlan workoutPlan = new WorkoutPlanBuilder()
                .path("/uploads/file")
                .id(1)
                .user(Arrays.asList(customer, pt))
                .uploadTime(LocalDateTime.now().plusMonths(-1))
                .build();
        workoutPlans.add(workoutPlan);
        workoutPlan = new WorkoutPlanBuilder()
                .path("/uploads/file2")
                .id(2)
                .user(Arrays.asList(customer, pt))
                .uploadTime(LocalDateTime.now())
                .build();
        workoutPlans.add(workoutPlan);
        return workoutPlans;
    }
}
