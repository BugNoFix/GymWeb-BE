package com.marcominaudo.gymweb.controller.dto.bodyDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBodyDetailsDTO {

    private int weight;

    private int bodyfat;

    private int height;

    private int chest;

    private int upperArm;

    private int shoulders;

    private int waist;

    private LocalDateTime uploadTime;
}
