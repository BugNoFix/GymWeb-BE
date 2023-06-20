package com.marcominaudo.gymweb.controller.dto.user;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserMapper {
    public UserDTO toDTO(User user){
        UserDTO userResponseDTO = new UserDTO();
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPrivacy(user.getPrivacy());
        userResponseDTO.setSurname(user.getSurname());
        userResponseDTO.setUuid(user.getUuid());
        userResponseDTO.setSubscriptionStart(user.getSubscriptionStart());
        userResponseDTO.setSubscriptionEnd(user.getSubscriptionEnd());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setActive(user.getIsActive());
        if(user.getPt() != null)
            userResponseDTO.setUuidPt(user.getPt().getUuid());
        return userResponseDTO;
    }

    public SearchUserDTO toDTO(Page<User> searchUsers) {
        SearchUserDTO searchUserDTO = new SearchUserDTO();
        List<UserDTO> userResponseDTOs = searchUsers.getContent().stream().map(this::toDTO).toList();

        searchUserDTO.setUsers(userResponseDTOs);
        searchUserDTO.setTotalPages(searchUserDTO.getTotalPages());
        searchUserDTO.setTotalElements(searchUsers.getTotalElements());
        return searchUserDTO;
    }

    public User toUser(UserDTO userDTO){
        return new UserBuilder().builder()
            .email(userDTO.getEmail())
            .name(userDTO.getName())
            .surname(userDTO.getSurname())
            .privacy(userDTO.isPrivacy())
            .isActive(userDTO.isActive())
            .subscriptionStart(userDTO.getSubscriptionStart())
            .subscriptionEnd(userDTO.getSubscriptionEnd())
            .role(userDTO.getRole())
            .password(userDTO.getPassword())
            .build();
    }
}
