package com.marcominaudo.gymweb.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBodyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int weight;

    private int bodyfat;

    private int height;

    private int chest;

    private int upperArm;

    private int shoulders;

    private int waist;

    @Column(nullable = false)
    private LocalDateTime uploadTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", nullable = false)
    User user;

    @PrePersist
    public void prePersist() {
        this.uploadTime = LocalDateTime.now();
    }
}
