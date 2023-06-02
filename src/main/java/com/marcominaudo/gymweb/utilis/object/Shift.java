package com.marcominaudo.gymweb.utilis.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startShift;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endShift;

    public String toString(){
        List<LocalDateTime> list =new ArrayList<>();
        list.add(startShift);
        list.add(endShift);
        return list.toString();
    }
}
