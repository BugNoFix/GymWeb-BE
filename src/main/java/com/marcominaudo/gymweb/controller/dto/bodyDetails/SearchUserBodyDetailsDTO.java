package com.marcominaudo.gymweb.controller.dto.bodyDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserBodyDetailsDTO {

    private List<UserBodyDetailsDTO> userBodyDetails;

    private int totalPages;

    private long totalElements;
}
