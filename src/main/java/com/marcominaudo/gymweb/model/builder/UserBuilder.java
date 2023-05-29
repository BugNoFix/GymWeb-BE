package com.marcominaudo.gymweb.model.builder;

import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserBuilder {

    private long id;

    private String uuid;

    private String email;

    private String name;

    private String surname;

    private String password;

    private LocalDateTime subscriptionStart;

    private LocalDateTime subscriptionEnd;

    private LocalDateTime created;

    private boolean privacy;

    private Role role;

    List<User> following = new ArrayList<>();

    public UserBuilder builder(){
        return new UserBuilder();
    }

    public UserBuilder name(String name){
        this.name = name;
        return this;
    }

    public UserBuilder id(long id){
        this.id = id;
        return this;
    }

    public UserBuilder uuid(String uuid){
        this.uuid = uuid;
        return this;
    }

    public UserBuilder email(String email){
        this.email = email;
        return this;
    }

    public UserBuilder surname(String surname){
        this.surname = surname;
        return this;
    }

    public UserBuilder password(String password){
        this.password = password;
        return this;
    }

    public UserBuilder subscriptionStart(LocalDateTime subscriptionStart){
        this.subscriptionStart = subscriptionStart;
        return this;
    }

    public UserBuilder subscriptionEnd(LocalDateTime subscriptionEnd){
        this.subscriptionEnd = subscriptionEnd;
        return this;
    }

    public UserBuilder created(LocalDateTime created){
        this.created = created;
        return this;
    }

    public UserBuilder privacy(boolean privacy){
        this.privacy = privacy;
        return this;
    }

    public UserBuilder role(Role role){
        this.role = role;
        return this;
    }

    public UserBuilder following(List<User> following){
        this.following = following;
        return this;
    }

    public User build(){
        return new User(id, uuid, email, name, surname, password, subscriptionStart, subscriptionEnd, created, privacy, role, following);
    }
}
