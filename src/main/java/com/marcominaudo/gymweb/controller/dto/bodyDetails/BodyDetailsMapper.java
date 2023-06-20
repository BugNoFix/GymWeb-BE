package com.marcominaudo.gymweb.controller.dto.bodyDetails;

import com.marcominaudo.gymweb.model.UserBodyDetails;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BodyDetailsMapper {
    public UserBodyDetailsDTO toDTO(UserBodyDetails userBodyDetails){
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

    public SearchUserBodyDetailsDTO toDTO(Page<UserBodyDetails> searchUserBodyDetails){
        SearchUserBodyDetailsDTO searchUserBodyDetailsDTO = new SearchUserBodyDetailsDTO();

        List<UserBodyDetailsDTO> userBodyDetailDTOs = searchUserBodyDetails.getContent().stream().map(this::toDTO).toList();

        searchUserBodyDetailsDTO.setUserBodyDetails(userBodyDetailDTOs);
        searchUserBodyDetailsDTO.setTotalPages(searchUserBodyDetails.getTotalPages());
        searchUserBodyDetailsDTO.setTotalElements(searchUserBodyDetails.getTotalElements());
        return searchUserBodyDetailsDTO;
    }

    public UserBodyDetails toBodyDetails(UserBodyDetailsDTO userBodyDetailsDTO){
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
