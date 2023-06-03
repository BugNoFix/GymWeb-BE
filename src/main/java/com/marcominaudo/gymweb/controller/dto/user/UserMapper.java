package com.marcominaudo.gymweb.controller.dto.user;

import com.marcominaudo.gymweb.model.User;
import com.marcominaudo.gymweb.model.UserBodyDetails;
import com.marcominaudo.gymweb.model.builder.UserBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
//TODO: implement builder pattern
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
        if(user.getPt() != null)
            userResponseDTO.setUuidPt(user.getPt().getUuid());
        return userResponseDTO;
    }

    public User UserRequestDTOToUser(UserRequestDTO userRequestDTO){
        return new UserBuilder().builder()
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .privacy(userRequestDTO.getPrivacy())
                .isActive(userRequestDTO.isActive)
                .uuid(userRequestDTO.getUuidPt())
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

    public List<UserBodyDetailsDTO> listOfBodyDetailsToDTO(List<UserBodyDetails> userBodyDetailsList){
        return userBodyDetailsList.stream().map(this::bodyDetailsToDTO).toList();
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

}
