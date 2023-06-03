package com.marcominaudo.gymweb.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserBodyDetailsDTO {

    List<UserBodyDetailsDTO> userBodyDetails;

    int totalPages;

    long totalElements;
}
