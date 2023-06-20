package com.marcominaudo.gymweb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @ManyToMany
    @JoinTable(
            name = "feedback_user",
            joinColumns = @JoinColumn(name = "feedbackId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    //@JoinColumn(name = "userId", nullable = false)
    List<User> users;

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();
    }

}
