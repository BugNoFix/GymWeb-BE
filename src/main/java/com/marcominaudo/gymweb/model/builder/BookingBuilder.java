package com.marcominaudo.gymweb.model.builder;

import com.marcominaudo.gymweb.model.Booking;
import com.marcominaudo.gymweb.model.Room;
import com.marcominaudo.gymweb.model.User;

import java.time.LocalDateTime;

public class BookingBuilder {

    private long id;
    private LocalDateTime subscriptionTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    User user;

    Room room;

    public BookingBuilder builder(){
        return new BookingBuilder();
    }

    public BookingBuilder subscriptionTime(LocalDateTime subscriptionTime){
        this.subscriptionTime = subscriptionTime;
        return this;
    }

    public BookingBuilder endTime(LocalDateTime endTime){
        this.endTime = endTime;
        return this;
    }

    public BookingBuilder startTime(LocalDateTime startTime){
        this.startTime = startTime;
        return this;
    }
    public BookingBuilder user(User user){
        this.user = user;
        return this;
    }

    public BookingBuilder room(Room room){
        this.room = room;
        return this;
    }

    public BookingBuilder id(long id){
        this.id = id;
        return this;
    }

    public Booking build(){
        return new Booking(id, subscriptionTime, startTime, endTime, user, room);
    }
}
