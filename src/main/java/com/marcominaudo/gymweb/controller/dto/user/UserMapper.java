package com.marcominaudo.gymweb.controller.dto.user;

import com.marcominaudo.gymweb.model.Role;
import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserMapper {
    public UserResponseDTO UserToUserResponseDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
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

    public User UserRequestDTOToUser(UserRequestDTO userRequestDTO){
        return new UserBuilder().builder()
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .privacy(userRequestDTO.isPrivacy())
                .isActive(userRequestDTO.getActive())
                .subscriptionStart(userRequestDTO.getSubscriptionStart())
                .subscriptionEnd(userRequestDTO.getSubscriptionEnd())
                .role(userRequestDTO.getRole())
                .password(userRequestDTO.getPassword())
                .build();
    }

    public UserBodyDetailsDTO bodyDetailsToDTO(UserBodyDetails userBodyDetails){
        UserBodyDetailsDTO userBodyDetailsDTO = new UserBodyDetailsDTO();
        userBodyDetailsDTO.setBodyfat(userBodyDetails.getBodyfat());
        userBodyDetailsDTO.setHeight(userBodyDetails.getHeight());
        userBodyDetailsDTO.setWaist(userBodyDetails.getWaist());
        userBodyDetailsDTO.setShoulders(userBodyDetails.getShoulders());
        userBodyDetailsDTO.setUpperArm(userBodyDetails.getUpperArm());
        userBodyDetailsDTO.setWeight(userBodyDetails.getWeight());
        userBodyDetailsDTO.setChest(userBodyDetails.getChest());
        userBodyDetailsDTO.setUploadTime(userBodyDetails.getUploadTime());
        return userBodyDetailsDTO;
    }

    public SearchUserBodyDetailsDTO listOfBodyDetailsToDTO(Page<UserBodyDetails> searchUserBodyDetails){
        SearchUserBodyDetailsDTO searchUserBodyDetailsDTO = new SearchUserBodyDetailsDTO();

        List<UserBodyDetailsDTO> userBodyDetailDTOs = searchUserBodyDetails.getContent().stream().map(this::bodyDetailsToDTO).toList();

        searchUserBodyDetailsDTO.setUserBodyDetails(userBodyDetailDTOs);
        searchUserBodyDetailsDTO.setTotalPages(searchUserBodyDetails.getTotalPages());
        searchUserBodyDetailsDTO.setTotalElements(searchUserBodyDetails.getTotalElements());
        return searchUserBodyDetailsDTO;
    }

    public UserBodyDetails DTOToBodyDetails(UserBodyDetailsDTO userBodyDetailsDTO){
        UserBodyDetails userBodyDetails = new UserBodyDetails();
        userBodyDetails.setBodyfat(userBodyDetailsDTO.getBodyfat());
        userBodyDetails.setHeight(userBodyDetailsDTO.getHeight());
        userBodyDetails.setWaist(userBodyDetailsDTO.getWaist());
        userBodyDetails.setShoulders(userBodyDetailsDTO.getShoulders());
        userBodyDetails.setUpperArm(userBodyDetailsDTO.getUpperArm());
        userBodyDetails.setWeight(userBodyDetailsDTO.getWeight());
        userBodyDetails.setChest(userBodyDetailsDTO.getChest());
        return userBodyDetails;
    }

    public SearchUserDTO listOfUsersToDTO(Page<User> searchUsers) {
        SearchUserDTO searchUserDTO = new SearchUserDTO();

        List<UserResponseDTO> userResponseDTOs = searchUsers.getContent().stream().map(this::UserToUserResponseDTO).toList();

        searchUserDTO.setUsers(userResponseDTOs);
        searchUserDTO.setTotalPages(searchUserDTO.getTotalPages());
        searchUserDTO.setTotalElements(searchUsers.getTotalElements());
        return searchUserDTO;
    }
}
